package com.fitness.activityservice.dtos;

import com.fitness.activityservice.constants.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ActivityRequest {
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionMatrics;
}
