package com.fitness.userservice.controller;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable UUID id){
        UserResponse userProfile = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfile);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        UserResponse register = userService.register(registerRequest);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateUser(@PathVariable UUID userId){
        Boolean exist = userService.existByUserId(userId);
        return ResponseEntity.ok(exist);
    }

}
