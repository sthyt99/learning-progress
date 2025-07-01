package com.example.learning_progress.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * パスワード変更API
 */
public class ChangePasswordRequest {

	@NotBlank
	private String currentPassword;

	@NotBlank
	@Size(min = 8, message = "新しいパスワードは8文字以上必要です")
	private String newPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
