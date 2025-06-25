package com.example.learning_progress.exception;

import java.time.LocalDateTime;

/**
 * 汎用エラー
 */
public class ErrorResponse {

	// 現在時刻
	private LocalDateTime timestamp;
	// ステータス
	private int status;
	// エラーコード
	private String errorCode;
	// メッセージ
	private String message;

	public ErrorResponse(LocalDateTime timestamp, int status, String errorCode, String message) {
		this.timestamp = timestamp;
		this.status = status;
		this.errorCode = errorCode;
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}
}
