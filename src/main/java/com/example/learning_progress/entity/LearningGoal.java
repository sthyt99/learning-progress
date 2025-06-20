package com.example.learning_progress.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * 学習目標
 */
@Entity
public class LearningGoal {

	/**
	 * ID（主キー）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * タイトル
	 */
	private String title;

	/**
	 * 目標時間
	 */
	private int targetHoure;

	/**
	 * 作成日
	 */
	private LocalDateTime createdAt;

	/**
	 * ユーザー情報
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ProgressLog> progressLogs;

	/**
	 * コンストラクタ
	 */
	public LearningGoal() {

	}

	/**
	 * IDを取得する
	 * @return id ID
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
	 * タイトルを取得する
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * タイトルを設定する
	 * @param title タイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 目標時間を取得する
	 * @return targetHoure
	 */
	public int getTargetHoure() {
		return targetHoure;
	}

	/**
	 * 目標時間を設定する
	 * @param targetHoure 目標時間
	 */
	public void setTargetHoure(int targetHoure) {
		this.targetHoure = targetHoure;
	}

	/**
	 * 作成日を取得する
	 * @return createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * 作成日を設定する
	 * @param createdAt 作成日
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * ユーザー情報を取得する
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * ユーザー情報を設定する
	 * @param user ユーザー情報
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 進捗情報を取得する
	 * @return progressLogs
	 */
	public List<ProgressLog> getProgressLogs() {
		return progressLogs;
	}

	/**
	 * 進捗情報を設定する
	 * @param progressLogs 進捗情報
	 */
	public void setProgressLogs(List<ProgressLog> progressLogs) {
		this.progressLogs = progressLogs;
	}
}
