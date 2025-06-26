package com.example.learning_progress.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.learning_progress.entity.ProgressLog;
import com.example.learning_progress.repository.ProgressLogRepository;

@Service
public class ProgressLogService {

	/**
	 * 進捗情報リポジトリ
	 */
	private final ProgressLogRepository logRepository;

	/**
	 * コンストラクタ
	 * @param progressLogRepository 進捗情報リポジトリ
	 */
	public ProgressLogService(ProgressLogRepository progressLogRepository) {
		this.logRepository = progressLogRepository;
	}

	/**
	 * 学習記録追加処理
	 * 
	 * @param log 進捗情報
	 * @return 進捗情報を保存する
	 */
	public ProgressLog addProgress(ProgressLog log) {

		// 進捗情報を追加する
		return logRepository.save(log);
	}

	/**
	 * 進捗情報取得処理
	 * 
	 * @param goalId ID
	 * @return 学習目標に紐づく進捗情報を全て取得する
	 */
	public List<ProgressLog> getLogsByGoalId(Long goalId) {

		// 最新の進捗より進捗情報を取得する
		return logRepository.findByGoalIdOrderByDateDesc(goalId);
	}

	/**
	 * 学習記録消去処理
	 * 
	 * @param logId ID
	 */
	public void deleteLog(Long logId) {

		// 対象行の進捗情報を消去する
		logRepository.deleteById(logId);
	}

	/**
	 * 全学習状況取得処理
	 * 
	 * @return 学習状況をすべて取得する
	 */
	public List<ProgressLog> findAll() {
		return logRepository.findAll();
	}
}
