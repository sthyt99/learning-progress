package com.example.learning_progress.dto.response;

import java.time.LocalDate;

/**
 * 進捗状況表示用の専用クラス
 */
public class ProgressLogDto {
	private Long id;
	private LocalDate date;
	private String description;
	private double hoursSpent;

	public ProgressLogDto(Long id, LocalDate date, String description, double hoursSpent) {
		this.id = id;
		this.date = date;
		this.description = description;
		this.hoursSpent = hoursSpent;
	}

	/**
	 * getter
	 */
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
}
