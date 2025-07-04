package com.example.learning_progress.dto.response;

import java.util.Set;

/**
 * ユーザー表示用専用クラス
 */
public class UserDto {

	private Long id;
	private String username;
	private Set<String> roles;

	public UserDto(Long id, String username, Set<String> roles) {
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	/**
	 *  getter
	 */
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public Set<String> getRoles() {
		return roles;
	}
}
