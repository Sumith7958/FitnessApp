package com.fitness.activityservice.service;

import com.fitness.activityservice.repo.ActivityRepository;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final WebClient userservicewebclient;
    @Autowired
    KafkaTemplate<String,Activity> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    //@CircuitBreaker(name = "userBreaker",fallbackMethod = "trackactivityfallback")
    @Retry(name = "userBreaker",fallbackMethod = "trackactivityfallback")
    public ResponseEntity<ActivityResponse>  trackActivity(ActivityRequest request) {

        if(!validateUser(request.getUserId())){
            throw new IllegalArgumentException("Invalid user");
        }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        kafkaTemplate.send(topic,savedActivity);

        return new ResponseEntity<>(mapToResponse(savedActivity),HttpStatus.OK) ;
    }
    public ResponseEntity<ActivityResponse> trackactivityfallback(ActivityRequest request, Throwable ex) {
        log.info("hello");
        return new ResponseEntity<>(new ActivityResponse(), HttpStatus.SERVICE_UNAVAILABLE);
    }


    private ActivityResponse mapToResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

    public boolean validateUser(String userId){
        try {
            return userservicewebclient.post()
                    .uri("/activityservice/validateuser")
                    .body(BodyInserters.fromValue(userId)) // Assuming userId is the value to send
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        }catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new RuntimeException("User Not Found: " + userId);
            else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                throw new RuntimeException("Invalid Request: " + userId);
        }
        return false;
    }
}
