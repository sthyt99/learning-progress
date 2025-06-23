package com.example.learning_progress.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

		List<String> errors = ex.getBindingResult()
				.getFieldErrors() // 個別のフィールドエラーを取得
				.stream()
				.map(error -> error.getField() + "は" + error.getDefaultMessage()) // 例:"username は 空白にできません"
				.collect(Collectors.toList());

		// 「400 Bad Request」で、日時・ステータスコード・エラーリストを含むオブジェクトを返却する
		return ResponseEntity.badRequest().body(
				new ValidationErrorResponse(LocalDateTime.now(), 400, errors));
	}

}
