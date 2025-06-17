package com.example.learning_progress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.learning_progress.entity.LearningGoal;
import com.example.learning_progress.repository.LearningGoalRepository;

@Service
public class LearningGoalService {

	/**
	 * 学習目標リポジトリ
	 */
	private final LearningGoalRepository goalRepository;

	/**
	 * コンストラクタ
	 * @param goalRepository 学習目標リポジトリ
	 */
	public LearningGoalService(LearningGoalRepository goalRepository) {
		this.goalRepository = goalRepository;
	}

	/**
	 * 学習目標登録処理
	 * 
	 * @param goal 学習目標
	 * @return 学習目標を保存する
	 */
	public LearningGoal createGoal(LearningGoal goal) {

		// 学習目標エンティティを保存する
		return goalRepository.save(goal);
	}

	/**
	 * 目標一覧取得処理
	 * 
	 * @param userId ユーザーID
	 * @return 目標一覧を取得する
	 */
	public List<LearningGoal> getGoalsByUserId(Long userId) {

		// ユーザーの目標一覧を取得する
		return goalRepository.findByUserId(userId);
	}

	/**
	 * 学習目標ID取得処理
	 * 
	 * @param id ID
	 * @return 目標IDを取得する
	 */
	public Optional<LearningGoal> findById(Long id) {

		// 一致する学習目標IDを取得する
		return goalRepository.findById(id);
	}
}
