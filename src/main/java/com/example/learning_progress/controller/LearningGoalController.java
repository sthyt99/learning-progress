package com.example.learning_progress.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning_progress.entity.LearningGoal;
import com.example.learning_progress.service.LearningGoalService;

@RestController
@RequestMapping("/api/goals")
public class LearningGoalController {

	/**
	 * 学習目標サービス
	 */
	private final LearningGoalService learningGoalService;
	
	/**
	 * コンストラクタ
	 * @param learningGoalService 学習目標サービス
	 */
	public LearningGoalController(LearningGoalService learningGoalService) {
		this.learningGoalService = learningGoalService;
	}
	
	@PostMapping
	public ResponseEntity<LearningGoal> createGoal(@RequestBody LearningGoal goal) {
		
		// サービス層の保存処理を実行し、保存した学習目標情報を取得する
		LearningGoal saved = learningGoalService.createGoal(goal);
		
		// 保存された学習目標情報を返却する
		return ResponseEntity.ok(saved);
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
