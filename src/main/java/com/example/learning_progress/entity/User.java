package com.example.learning_progress.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * ユーザー情報
 */
@Entity
@Table(name = "users")
public class User {

	/**
	 * ID（主キー）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * ユーザー名
	 */
	@Column(nullable = false, unique = true)
	private String username;

	/**
	 * パスワード
	 */
	@Column(nullable = false)
	private String password;

	/**
	 * 権限
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private Set<String> roles = new HashSet<>();

	/**
	 * 学習目標
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<LearningGoal> learningGoals;

	/**
	 * コンストラクタ
	 */
	public User() {
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
	 * ユーザー名を取得する
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * ユーザー名を設定する
	 * @param username ユーザー名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * パスワードを取得する
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定する
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 権限を取得する
	 * @return roles
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * 権限を設定する
	 * @param password 権限
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/**
	 * 学習目標を取得する
	 * @return learningGoals
	 */
	public List<LearningGoal> getLearningGoals() {
		return learningGoals;
	}

	/**
	 * 学習目標を設定する
	 * @param learningGoals 学習目標
	 */
	public void setLearningGoals(List<LearningGoal> learningGoals) {
		this.learningGoals = learningGoals;
	}
}
