package com.example.learning_progress.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning_progress.entity.ProgressLog;
import com.example.learning_progress.service.ProgressLogService;

@RestController
@RequestMapping("/api/progress")
public class ProgressLogController {

	/**
	 * 学習進捗サービス
	 */
	private final ProgressLogService logService;

	/**
	 * コンストラクタ
	 * @param logService 学習進捗サービス
	 */
	public ProgressLogController(ProgressLogService logService) {
		this.logService = logService;
	}

	@PostMapping
	public ResponseEntity<ProgressLog> addLog(@RequestBody ProgressLog log) {

		// サービス層の保存処理を実行し、保存した学習進捗情報を取得する
		ProgressLog saved = logService.addProgress(log);
		
		// 新しく作成されたリソースの場所（URI）をヘッダーに記述する
		URI location = URI.create("/api/progress/" + saved.getId());

		// 保存された学習進捗情報をJSONボディへ返却する
		return ResponseEntity.created(location).body(saved);
	}

	@GetMapping("/goal/{goalId}")
	public ResponseEntity<List<ProgressLog>> getLogs(@PathVariable Long goalId) {

		// サービス層の処理を実行し、IDから学習進捗一覧を取得する
		List<ProgressLog> goals = logService.getLogsByGoalId(goalId);

		// 学習進捗一覧を返却する
		return ResponseEntity.ok(goals);
	}

	@DeleteMapping("/{logId}")
	public ResponseEntity<Void> deleteLog(@PathVariable Long logId) {
		
		// サービス層の処理を実行し、対象の学習進捗情報を消去する
		logService.deleteLog(logId);
		
		// 「204 No Content」データなし、成功を返却する
		return ResponseEntity.noContent().build();
	}
}
