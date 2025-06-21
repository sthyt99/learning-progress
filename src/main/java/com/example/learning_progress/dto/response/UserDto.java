package com.example.learning_progress.dto.response;

/**
 * ユーザー表示用専用クラス
 */
public class UserDto {

	private Long id;
	private String username;

	public UserDto(Long id, String username) {
		this.id = id;
		this.username = username;
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
}
