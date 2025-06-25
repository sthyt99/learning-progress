package com.example.learning_progress.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

/**
 * 共通エラーハンドリング
 * @RestControllerAdvice : 全てのコントローラで発生した例外を一括ハンドリング
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * @Valid、@Validatedによるエラー発生の際、呼び出される
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidatioinErrors(MethodArgumentNotValidException ex) {

		List<ValidationErrorDetail> errors = ex.getBindingResult()
				.getFieldErrors() // 個別のフィールドエラーを取得
				.stream()
				.map(error -> new ValidationErrorDetail(
						error.getField(),
						error.getField() + "は" + error.getDefaultMessage(), // 例:"username は 空白にできません"
						error.getCode())) // バリデーションコード例: NotBlank, Min
				.collect(Collectors.toList());

		// 「400 Bad Request」で、日時・ステータスコード・エラーリストを含むオブジェクトを返却する
		return ResponseEntity.badRequest().body(
				new ValidationErrorResponse(LocalDateTime.now(), 400, errors));
	}

	/**
	 * データが見つからない場合
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorResponse(
						LocalDateTime.now(),
						HttpStatus.NOT_FOUND.value(),
						"NOT_FOUND",
						ex.getMessage()));
	}

	/**
	 * 不正リクエスト
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new ErrorResponse(
						LocalDateTime.now(),
						400,
						"BAD_REQUEST",
						ex.getMessage()));
	}

	/**
	 * 汎用例外（予期せぬエラー）
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnexpexted(Exception ex) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new ErrorResponse(
						LocalDateTime.now(),
						500,
						"INTERNAL_ERROR",
						"予期せぬエラーが発生しました"));
	}

	/**
	 * 独自例外を呼び出す
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessError(BusinessException ex) {

		ErrorCode code = ex.getErrorCode();

		return ResponseEntity.status(code.getHttpStatus()).body(
				new ErrorResponse(
						LocalDateTime.now(),
						code.getHttpStatus().value(),
						code.getCode(),
						ex.getMessage()));
	}
}
