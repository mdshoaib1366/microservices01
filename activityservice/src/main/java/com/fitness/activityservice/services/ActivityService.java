package com.fitness.activityservice.services;

import com.fitness.activityservice.dtos.ActivityRequest;
import com.fitness.activityservice.dtos.ActivityResponse;

import java.util.List;

public interface ActivityService {
    public ActivityResponse trackActivity(ActivityRequest activityRequest);
    public List<ActivityResponse> userActivities(String userId);
    ActivityResponse getActivityById(Long activityId);

}
