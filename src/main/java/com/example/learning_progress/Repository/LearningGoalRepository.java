package com.example.learning_progress.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learning_progress.entity.LearningGoal;

@Repository
public interface LearningGoalRepository extends JpaRepository<LearningGoal, Long>  {

	List<LearningGoal> findByUserId(Long userId);
}