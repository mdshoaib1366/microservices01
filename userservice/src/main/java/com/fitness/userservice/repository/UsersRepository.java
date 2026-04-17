package com.fitness.userservice.repository;

import com.fitness.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    public User findByEmail(String email);
    public Boolean existsByEmail(String email);
}
