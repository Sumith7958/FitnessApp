package com.aiservice.Aiservice.Repo;

import com.aiservice.Aiservice.Model.Recommendations;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecommendationRepository extends MongoRepository<Recommendations,String> {

    List<Recommendations> findByUserId(String userId);

    Recommendations findByActivityId(String activityId);
}
