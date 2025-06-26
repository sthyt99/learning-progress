package com.example.learning_progress.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning_progress.entity.LearningGoal;
import com.example.learning_progress.entity.ProgressLog;
import com.example.learning_progress.entity.User;
import com.example.learning_progress.service.LearningGoalService;
import com.example.learning_progress.service.ProgressLogService;
import com.example.learning_progress.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final UserService userService;
	private final LearningGoalService goalService;
	private final ProgressLogService logService;

	public AdminController(UserService userService, LearningGoalService goalService, ProgressLogService logService) {
		this.userService = userService;
		this.goalService = goalService;
		this.logService = logService;
	}

	/**
	 * 管理者のみ全ユーザーを取得する
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.findAll();
	}

	/**
	 * 管理者のみ全学習目標を取得する
	 */
	@GetMapping("/goals")
	public List<LearningGoal> getAllGoals() {
		return goalService.findAll();
	}

	/**
	 * 管理者のみ全学習状況を取得する
	 */
	@GetMapping("/logs")
	public List<ProgressLog> getAllLogs() {
		return logService.findAll();
	}
}
