package com.aiservice.Aiservice.Controller;

import com.aiservice.Aiservice.Model.Recommendations;
import com.aiservice.Aiservice.Service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class AiServiceController {

    @Autowired
    AiService aiService;

    @GetMapping("/user")
    public ResponseEntity<List<Recommendations>> getUserRecommendation(@RequestBody String userId) {
        return ResponseEntity.ok(aiService.getUserRecommendation(userId));
    }

    @GetMapping("/activity")
    public ResponseEntity<Recommendations> getActivityRecommendation(@PathVariable String activityId) {
        return ResponseEntity.ok(aiService.getActivityRecommendation(activityId));
    }

}
