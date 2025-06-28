package com.example.learning_progress.dto.response;

import java.time.LocalDate;

/**
 * 管理者用の学習進捗DTO
 */
public class AdminProgressLogDto {

	private Long id;
	private LocalDate date;
	private String description;
	private double hoursSpent;
	private String goalTitle;
	private String username;

	public AdminProgressLogDto(Long id, LocalDate date, String description, double hoursSpent, String goalTitle,
			String username) {
		this.id = id;
		this.date = date;
		this.description = description;
		this.hoursSpent = hoursSpent;
		this.goalTitle = goalTitle;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getHoursSpent() {
		return hoursSpent;
	}

	public String getGoalTitle() {
		return goalTitle;
	}

	public String getUsername() {
		return username;
	}
}
