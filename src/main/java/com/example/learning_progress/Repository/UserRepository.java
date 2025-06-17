package com.example.learning_progress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learning_progress.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}