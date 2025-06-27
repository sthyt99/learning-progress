package com.example.learning_progress.dto.response;

import java.time.LocalDateTime;

/**
 * 管理者表示用 学習目標DTO
 */
public class AdminLearningGoalDto {
	private Long id;
	private String title;
	private int targetHours;
	private LocalDateTime createdAt;
	private String ownerUsername;

	public AdminLearningGoalDto(Long id, String title, int targetHours, LocalDateTime createdAt, String ownerUsername) {
		this.id = id;
		this.title = title;
		this.targetHours = targetHours;
		this.createdAt = createdAt;
		this.ownerUsername = ownerUsername;
	}

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

	public String getOwnerUsername() {
		return ownerUsername;
	}
}