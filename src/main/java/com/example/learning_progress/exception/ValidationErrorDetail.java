package com.example.learning_progress.exception;

/**
 * フィールド、メッセージ、コード
 * エラー項目
 */
public class ValidationErrorDetail {

	private String field;
	private String message;
	private String code;

	public ValidationErrorDetail(String field, String message, String code) {
		this.field = field;
		this.message = message;
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}
}
