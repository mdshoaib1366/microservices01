package com.fitness.activityservice.dtos;

import com.fitness.activityservice.constants.ActivityType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ActivityResponse {
    private Long id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionMatrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
