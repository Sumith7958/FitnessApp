package com.aiservice.Aiservice.Service;

import com.aiservice.Aiservice.Model.Recommendations;
import com.aiservice.Aiservice.Repo.RecommendationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AiService {
    private final RecommendationRepository recommendationRepository;

    public List<Recommendations> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendations getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId);
    }
}
