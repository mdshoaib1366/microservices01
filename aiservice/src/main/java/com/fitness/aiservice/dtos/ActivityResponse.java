package com.fitness.aiservice.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ActivityResponse {
    private Long id;
    private String userId;
    private String type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMatrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    public enum ActivityType {
//        RUNNING,WALKING,CYCLING,SWIMMING,WEIGHT_TRAINING,CARDIO,STRETCHING,OTHERS
//    }

}
