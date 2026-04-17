package com.fitness.aiservice.services;

import com.fitness.aiservice.dtos.ActivityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final AIActivityService activityService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(ActivityResponse activity){
        log.info("Receiving activity for proccessing: {}", activity.getId());
        log.info("Generated recommendation: {}", activityService.generateRecommendation(activity));
    }
}
