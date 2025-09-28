package com.aiservice.Aiservice.Service;
import com.aiservice.Aiservice.Service.ActivityAIService;
import com.aiservice.Aiservice.Model.Recommendations;
import com.aiservice.Aiservice.Repo.RecommendationRepository;
import com.aiservice.Aiservice.dto.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AiService {
    @Autowired
    RecommendationRepository recommendationRepository;
    @Autowired
    ActivityAIService activityAIService;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "recommendations_listerner")
    public void reccomandations(Activity activity){
        log.info("here is the user id {}",activity.getUserId());
        Recommendations recommendationsData= activityAIService.generateRecommendation(activity);
        log.info("here is the user id {}",recommendationsData);
        recommendationRepository.save(recommendationsData);
    }

    public void reccomandationstest(Activity activity){
        log.info("here is the user id {}",activity.getUserId());
        Recommendations recommendationsData= activityAIService.generateRecommendation(activity);
        log.info("here is the user id {}",recommendationsData);
        recommendationRepository.save(recommendationsData);
    }

    public List<Recommendations> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendations getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId);
    }
}
