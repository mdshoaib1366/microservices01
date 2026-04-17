package com.fitness.aiservice.services.Impl;

import com.fitness.aiservice.dtos.RecommendationResponse;
import com.fitness.aiservice.models.Recommendation;
import com.fitness.aiservice.repositories.RecommendationRepository;
import com.fitness.aiservice.services.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    @Override
    public List<RecommendationResponse> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId)
                .stream().map(this::entityToResponse).toList();
    }

    @Override
    public RecommendationResponse getActivityRecommendation(Long activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .map(this::entityToResponse)
                .orElseThrow(() -> new RuntimeException("No recommendation found for this activity: "+activityId));
    }

    private RecommendationResponse entityToResponse(Recommendation recommendation){
        return RecommendationResponse.builder()
                .build();
    }

}
