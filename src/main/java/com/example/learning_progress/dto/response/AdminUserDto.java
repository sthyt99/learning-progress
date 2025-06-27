package com.example.learning_progress.dto.response;

import java.util.Set;

/**
 * 管理者用ユーザーDTO
 */
public class AdminUserDto {
	private Long id;
	private String username;
	private Set<String> roles;

	public AdminUserDto(Long id, String username, Set<String> roles) {
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

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