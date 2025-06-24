package com.example.learning_progress.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * エラーレスポンス用クラス
 */
public class ValidationErrorResponse {

	private LocalDateTime timestamp;
	private int status;
	private List<ValidationErrorDetail> errors;

	public ValidationErrorResponse(LocalDateTime timestamp, int status, List<ValidationErrorDetail> errors) {
		this.timestamp = timestamp;
		this.status = status;
		this.errors = errors;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public List<ValidationErrorDetail> getErrors() {
		return errors;
	}
}
