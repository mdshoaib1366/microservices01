package com.fitness.aiservice.repositories;

import com.fitness.aiservice.dtos.RecommendationResponse;
import com.fitness.aiservice.models.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, Integer> {
    List<Recommendation> findByUserId(String userId);
    Optional<Recommendation> findByActivityId(Long activityId);

}
