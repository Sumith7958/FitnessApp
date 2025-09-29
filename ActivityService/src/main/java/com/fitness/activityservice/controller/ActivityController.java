package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request){
        return activityService.trackActivity(request);
    }

    @GetMapping
    @RateLimiter(name = "userBreaker",fallbackMethod = "trackactivityfallback")
    public ResponseEntity<Boolean> hello(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    public ResponseEntity<Boolean> trackactivityfallback(Exception ex){return new ResponseEntity<>(false, HttpStatus.OK);}
}
