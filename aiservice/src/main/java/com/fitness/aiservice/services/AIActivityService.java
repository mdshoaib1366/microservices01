package com.fitness.aiservice.services;

import com.fitness.aiservice.dtos.ActivityResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class AIActivityService {

    private final OpenAIService openAIService;

    public String generateRecommendation(ActivityResponse activity){
        String prompt = this.createPromptForActivity(activity);
        String aiResponse = openAIService.askToChatGpt(prompt);
        log.info("Response from ai: {}",aiResponse);
        return aiResponse;
    }

    private String createPromptForActivity(ActivityResponse activityResponse){
        return String.format(""" 
                  Analyze this fitness activity and provide detailed recommendations in the following format
                  {
                      "analysis" : {
                          "overall": "Overall analysis here",
                          "pace": "Pace analysis here",
                          "heartRate": "Heart rate analysis here",
                          "CaloriesBurned": "Calories Burned here"
                      },
                      "improvements": [
                          {
                              "area": "Area name",
                              "recommendation": "Detailed Recommendation"
                          }
                      ],
                      "suggestions" : [
                          {
                              "workout": "Workout name",
                              "description": "Detailed workout description"
                          }
                      ],
                      "safety": [
                          "Safety point 1",
                          "Safety point 2"
                      ]
                  }
                
                  Analyze this activity:
                  Activity Type: %s
                  Duration: %d minutes
                  calories Burned: %d
                  Additional Metrics: %s
                
                  provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines
                  Ensure the response follows the EXACT JSON format shown above.    
                """
        ,
                activityResponse.getType(),
                activityResponse.getDuration(),
                activityResponse.getCaloriesBurned(),
                activityResponse.getAdditionalMatrics());
    }
}
