package com.example.learning_progress.exception;

import org.springframework.http.HttpStatus;

/**
 * エラーコードの共通定義
 */
public enum ErrorCode {

	// 400系
	BAD_REQUEST("BAD_REQUEST", HttpStatus.BAD_REQUEST, "不正なリクエストです"),
	DUPLICATE_OPERATION("DUPLICATE_OPERATION", HttpStatus.BAD_REQUEST, "すでに登録されています"),
	UNAUTHORIZED_ACTION("UNAUTHORIZED_ACTION", HttpStatus.BAD_REQUEST, "許可されていない操作です"),
	BUSINESS_CONSTRAINT_VIOLATION("BUSINESS_CONSTRAINT_VIOLATION", HttpStatus.BAD_REQUEST, "業務ルール違反です"),

	// 404系
	NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND, "リソースが見つかりません"),

	// 500系
	INTERNAL_ERROR("INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "サーバーエラーが発生しました");

	private final String code;
	private final HttpStatus status;
	private final String defaultMessage;

	ErrorCode(String code, HttpStatus status, String defaultMessage) {
		this.code = code;
		this.status = status;
		this.defaultMessage = defaultMessage;
	}

	public String getCode() {
		return code;
	}

	public HttpStatus getHttpStatus() {
		return status;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}
}
