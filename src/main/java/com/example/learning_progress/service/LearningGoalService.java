package com.example.learning_progress.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.learning_progress.entity.LearningGoal;
import com.example.learning_progress.entity.User;
import com.example.learning_progress.exception.BusinessException;
import com.example.learning_progress.exception.ErrorCode;
import com.example.learning_progress.repository.LearningGoalRepository;

import jakarta.persistence.EntityNotFoundException;

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
	public LearningGoal createGoalForUser(User user, String title, int targetHours) {

		// 同じ目標がある時、trueを返却する。
		boolean exists = goalRepository.findByUserId(user.getId()).stream()
				.anyMatch(g -> g.getTitle().equalsIgnoreCase(title)); // 大文字、小文字を区別しない

		// 同じタイトルが存在する場合、業務例外をスローする
		if (exists) {
			throw new BusinessException("同じタイトルの目標は既に存在します", ErrorCode.DUPLICATE_OPERATION);
		}

		// 新規目標を作成する
		LearningGoal goal = new LearningGoal();
		goal.setTitle(title);
		goal.setTargetHours(targetHours);
		goal.setUser(user);
		goal.setCreatedAt(LocalDateTime.now());

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
	public LearningGoal findByIdOrThrow(Long id) {

		// 一致する学習目標IDを取得する
		return goalRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("指定された学習目標が存在しません"));
	}

	/**
	 * 全学習目標取得処理(管理者用)
	 * 
	 * @return 学習目標をすべて取得する
	 */
	public List<LearningGoal> findAll() {
		return goalRepository.findAll();
	}
}
