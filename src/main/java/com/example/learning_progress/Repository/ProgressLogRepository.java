package com.example.learning_progress.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learning_progress.entity.ProgressLog;

@Repository
public interface ProgressLogRepository extends JpaRepository<ProgressLog, Long> {

	List<ProgressLog> findByGoalIdOrderByDateDesc(Long goalId);
}
