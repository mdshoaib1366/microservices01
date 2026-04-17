package com.fitness.aiservice.controllers;

import com.fitness.aiservice.dtos.RecommendationResponse;
import com.fitness.aiservice.services.RecommendationService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recommendations")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void checkDB() {
        System.out.println("Connected DB: " + mongoTemplate.getDb().getName());
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationResponse>> getUserRecommendation(@PathVariable String userId){
        List<RecommendationResponse> userRecommendation = recommendationService.getUserRecommendation(userId);
        return new ResponseEntity<>(userRecommendation, HttpStatus.OK);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<RecommendationResponse> getActivityRecommendation(@PathVariable Long activityId){
        RecommendationResponse activityRecommendation = recommendationService.getActivityRecommendation(activityId);
        return new ResponseEntity<>(activityRecommendation, HttpStatus.OK);
    }
}
