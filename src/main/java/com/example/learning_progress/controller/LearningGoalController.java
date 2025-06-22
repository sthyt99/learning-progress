package com.example.learning_progress.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning_progress.dto.request.LearningGoalRequest;
import com.example.learning_progress.dto.response.LearningGoalDto;
import com.example.learning_progress.entity.LearningGoal;
import com.example.learning_progress.entity.User;
import com.example.learning_progress.service.LearningGoalService;
import com.example.learning_progress.service.UserService;

import jakarta.validation.Valid;

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
	public ResponseEntity<LearningGoalDto> createGoal(@Valid @RequestBody LearningGoalRequest request) {

		// ログイン認証済みユーザーのユーザー名を取得する
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// ユーザ名を検索し、ユーザー情報を取得する。存在しない場合、例外をスローする。
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// 学習目標のフィールドを設定する
		LearningGoal goal = new LearningGoal();
		goal.setTitle(request.getTitle());
		goal.setTargetHours(request.getTargetHours());
		goal.setUser(user);
		goal.setCreatedAt(LocalDateTime.now());

		// 学習目標を登録する
		LearningGoal saved = learningGoalService.createGoal(goal);

		// DTOに保存する
		LearningGoalDto dto = new LearningGoalDto(
				saved.getId(),
				saved.getTitle(),
				saved.getTargetHours(),
				saved.getCreatedAt());

		// 保存された学習目標情報を返却する
		return ResponseEntity.ok(dto);
	}

	/**
	 * 自分の目標を取得する
	 */
	@GetMapping("/me")
	public ResponseEntity<List<LearningGoalDto>> getMyGoals() {

		// ログイン認証済みユーザーのユーザー名を取得する
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// ユーザ名を検索し、ユーザー情報を取得する。存在しない場合、例外をスローする。
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// DTOに変換（エンティティを直接返さない）
		List<LearningGoalDto> result = learningGoalService.getGoalsByUserId(user.getId())
				.stream()
				.map(goal -> new LearningGoalDto(
						goal.getId(),
						goal.getTitle(),
						goal.getTargetHours(),
						goal.getCreatedAt()))
				.collect(Collectors.toList());

		// 目標を取得する
		return ResponseEntity.ok(result);
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
