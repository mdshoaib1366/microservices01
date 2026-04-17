package com.fitness.activityservice.services.Impl;

import com.fitness.activityservice.dtos.ActivityRequest;
import com.fitness.activityservice.dtos.ActivityResponse;
import com.fitness.activityservice.models.Activity;
import com.fitness.activityservice.repositories.ActivityRepository;
import com.fitness.activityservice.services.ActivityService;
import com.fitness.activityservice.services.UserValidationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final static Logger LOG = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    public ActivityServiceImpl(ActivityRepository activityRepository, UserValidationService userValidationService, RabbitTemplate rabbitTemplate) {
        this.activityRepository = activityRepository;
        this.userValidationService = userValidationService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Override
    @Transactional
    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        Boolean isValidUser = userValidationService.validateUser(activityRequest.getUserId());

        if(!isValidUser){
            throw new RuntimeException("Invalid user: "+activityRequest.getUserId());
        }

        Activity newActivity = Activity.builder()
                .id(System.currentTimeMillis())
                .userId(activityRequest.getUserId())
                .type(activityRequest.getType())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .duration(activityRequest.getDuration())
                .additionMatrics(activityRequest.getAdditionMatrics())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Activity savedActivity = activityRepository.save(newActivity);

        // Publish to RabbitMQ for AI Response
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        } catch (Exception e) {
            LOG.error("Failed to publish activity to RabbitMQ: ",e);
        }

        return this.entityToResponse(savedActivity);
    }

    @Override
    public List<ActivityResponse> userActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activities.stream()
                .map(this::entityToResponse).toList();
    }

    @Override
    public ActivityResponse getActivityById(Long activityId) {
        return activityRepository.findById(activityId)
                .map(this::entityToResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found with Id: "+activityId));
    }

    private ActivityResponse entityToResponse(Activity activity){
        return  ActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .duration(activity.getDuration())
                .startTime(activity.getStartTime())
                .caloriesBurned(activity.getCaloriesBurned())
                .additionMatrics(activity.getAdditionMatrics())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }
}
