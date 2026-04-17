package com.fitness.aiservice.services;

import com.fitness.aiservice.dtos.RecommendationResponse;

import java.util.List;
import java.util.UUID;

public interface RecommendationService {
    List<RecommendationResponse> getUserRecommendation(String userId);
    RecommendationResponse getActivityRecommendation(Long activityId);

}
