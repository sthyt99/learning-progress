package com.example.learning_progress.dto.response;

import java.time.LocalDateTime;

/**
 * 学習目標表示用の専用クラス
 */
public class LearningGoalDto {

	private Long id;
	private String title;
	private int targetHours;
	private LocalDateTime createdAt;

	public LearningGoalDto(Long id, String title, int targetHours, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.targetHours = targetHours;
		this.createdAt = createdAt;
	}

	/**
	 * getter
	 */
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getTargetHours() {
		return targetHours;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}