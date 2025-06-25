package com.example.learning_progress.exception;

/**
 * 独自例外
 */
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
	
	public BusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
