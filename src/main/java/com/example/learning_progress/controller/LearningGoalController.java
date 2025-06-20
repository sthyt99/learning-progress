package com.example.learning_progress.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning_progress.entity.LearningGoal;
import com.example.learning_progress.entity.User;
import com.example.learning_progress.service.LearningGoalService;
import com.example.learning_progress.service.UserService;

@RestController
@RequestMapping("/api/goals")
public class LearningGoalController {

	private final LearningGoalService learningGoalService;
	private final UserService userService;

	/**
	 * コンストラクタ
	 */
	public LearningGoalController(LearningGoalService learningGoalService, UserService userService) {
		this.learningGoalService = learningGoalService;
		this.userService = userService;
	}

	/**
	 * ログイン中のユーザーに目標を追加する
	 */
	@PostMapping
	public ResponseEntity<LearningGoal> createGoal(@RequestBody LearningGoal goal) {

		// ログイン認証済みユーザーのユーザー名を取得する
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// ユーザ名を検索し、ユーザー情報を取得する。存在しない場合、例外をスローする。
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// ユーザー情報を設定する
		goal.setUser(user);

		// サービス層の保存処理を実行し、保存した学習目標情報を取得する
		LearningGoal saved = learningGoalService.createGoal(goal);

		// 保存された学習目標情報を返却する
		return ResponseEntity.ok(saved);
	}

	/**
	 * 自分の目標を取得する
	 */
	@GetMapping("/me")
	public ResponseEntity<List<LearningGoal>> getMyGoals() {

		// ログイン認証済みユーザーのユーザー名を取得する
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// ユーザ名を検索し、ユーザー情報を取得する。存在しない場合、例外をスローする。
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// 目標を取得する
		return ResponseEntity.ok(learningGoalService.getGoalsByUserId(user.getId()));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<LearningGoal>> getGoalsByUser(@PathVariable Long userId) {

		// サービス層の処理を実行し、IDから学習目標一覧を取得する
		List<LearningGoal> goals = learningGoalService.getGoalsByUserId(userId);

		// 学習目標一覧を返却する
		return ResponseEntity.ok(goals);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LearningGoal> findById(@PathVariable Long id) {

		// サービス層の処理を実行し、IDから学習目標情報を取得する
		Optional<LearningGoal> goal = learningGoalService.findById(id);

		// 存在する場合(「200 OK」)、学習目標情報を返却し、存在しない場合「404 NOT FOUND」を返却する
		return goal.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
