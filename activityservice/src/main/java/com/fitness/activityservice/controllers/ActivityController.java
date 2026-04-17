package com.fitness.activityservice.controllers;

import com.fitness.activityservice.dtos.ActivityRequest;
import com.fitness.activityservice.dtos.ActivityResponse;
import com.fitness.activityservice.services.ActivityService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@AllArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void checkDB() {
        System.out.println("Connected DB: " + mongoTemplate.getDb().getName());
    }


    @PostMapping("/")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest){
        ActivityResponse register = activityService.trackActivity(activityRequest);
        return new ResponseEntity<ActivityResponse>(register, HttpStatus.CREATED);
    }

    @GetMapping("/show/all")
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader String userId){
        List<ActivityResponse> activityResponses = activityService.userActivities(userId);
        return new ResponseEntity<List<ActivityResponse>>(activityResponses, HttpStatus.OK);
    }

    @GetMapping("/show/{activityId}")
    public ResponseEntity<ActivityResponse> getById(@PathVariable Long activityId){
        ActivityResponse activity = activityService.getActivityById(activityId);
        return new ResponseEntity<ActivityResponse>(activity, HttpStatus.OK);
    }

}
