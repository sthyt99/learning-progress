package com.example.learning_progress.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 進捗情報
 */
@Entity
public class ProgressLog {

	/**
	 * ID（主キー）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 学習日
	 */
	private LocalDate date;

	/**
	 * 学習内容説明
	 */
	private String description;

	/**
	 * 学習時間
	 */
	private double hoursSpent;

	/**
	 * 学習目標
	 */
	@ManyToOne
	@JoinColumn(name = "goal_id")
	private LearningGoal goal;

	/**
	 * コンストラクタ
	 */
	public ProgressLog() {
	}

	/**
	 * IDを取得する
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * IDを設定する
	 * @param id ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 学習日を取得する
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * 学習日を設定する
	 * @param date 学習日
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * 学習内容説明を取得する
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 学習内容説明を設定する
	 * @param description 学習内容説明
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 学習時間を取得する
	 * @return hoursSpent
	 */
	public double getHoursSpent() {
		return hoursSpent;
	}

	/**
	 * 学習時間を設定する
	 * @param hoursSpent 学習時間
	 */
	public void setHoursSpent(double hoursSpent) {
		this.hoursSpent = hoursSpent;
	}

	/**
	 * 学習目標を取得する
	 * @return goal
	 */
	public LearningGoal getGoal() {
		return goal;
	}

	/**
	 * 学習目標を設定する
	 * @param goal 学習目標
	 */
	public void setGoal(LearningGoal goal) {
		this.goal = goal;
	}
}
