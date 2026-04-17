package com.fitness.activityservice.services.Impl;

import com.fitness.activityservice.services.UserValidationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@AllArgsConstructor
public class UserValidationServiceImpl implements UserValidationService {

    private final static Logger LOG = LoggerFactory.getLogger(UserValidationServiceImpl.class);
    private final WebClient userWebClient;

    @Override
    public Boolean validateUser(String userId) {
        LOG.info("Calling User Validation API for userId: {}",userId);
        try {
            return userWebClient.get()
                    .uri("/api/users/validate/{userid}",userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        }
        catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new RuntimeException("User not found: "+userId);
            }
            else if(e.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new RuntimeException("Invalid request.");
            }
        }
        return false;
    }
}
