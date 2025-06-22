package com.example.learning_progress.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 進捗状況の登録専用（POST)
 */
public class ProgressLogRequest {

	@NotNull
	private Long goalId;

	@NotNull
	private LocalDate date;

	@NotBlank
	private String description;

	@Min(0)
	private double hoursSpent;

	// getter/setter
	public Long getGoalId() {
		return goalId;
	}

	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getHoursSpent() {
		return hoursSpent;
	}

	public void setHoursSpent(double hoursSpent) {
		this.hoursSpent = hoursSpent;
	}
}
