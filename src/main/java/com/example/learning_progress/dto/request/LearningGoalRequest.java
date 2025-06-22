package com.example.learning_progress.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * 学習目標の登録専用（POST）
 */
public class LearningGoalRequest {

	@NotBlank
	private String title;

	@Min(1)
	private int targetHours;

	/**
	 * getter/setter
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTargetHours() {
		return targetHours;
	}

	public void setTargetHours(int targetHours) {
		this.targetHours = targetHours;
	}
}
