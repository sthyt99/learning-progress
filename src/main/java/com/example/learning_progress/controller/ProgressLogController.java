package com.example.learning_progress.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning_progress.dto.request.ProgressLogRequest;
import com.example.learning_progress.dto.response.ProgressLogDto;
import com.example.learning_progress.entity.LearningGoal;
import com.example.learning_progress.entity.ProgressLog;
import com.example.learning_progress.entity.User;
import com.example.learning_progress.exception.BusinessException;
import com.example.learning_progress.exception.ErrorCode;
import com.example.learning_progress.service.LearningGoalService;
import com.example.learning_progress.service.ProgressLogService;
import com.example.learning_progress.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/progress")
public class ProgressLogController {

	private final ProgressLogService logService;
	private final UserService userService;
	private final LearningGoalService goalService;

	/**
	 * コンストラクタ
	 */
	ProgressLogController(ProgressLogService logService, UserService userService, LearningGoalService goalService) {
		this.logService = logService;
		this.userService = userService;
		this.goalService = goalService;
	}

	/**
	 * 学習記録を追加する
	 */
	@PostMapping
	public ResponseEntity<?> addLog(@Valid @RequestBody ProgressLogRequest request) {

		// ログイン認証済みユーザーのユーザー名を取得する
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// ユーザ名を検索し、ユーザー情報を取得する。存在しない場合、例外をスローする。
		User user = userService.findByUsernameOrThrow(username);

		// 対象の目標がログインユーザーのものであることを検証する
		LearningGoal goal = goalService.findByIdOrThrow(request.getGoalId());

		// 学習目標のIDが認証済みユーザーのIDと一致しない場合
		if (!Objects.equals(goal.getUser().getId(), user.getId())) {

			// 例外をスローする
			throw new BusinessException("他人の目標には記録できません", ErrorCode.UNAUTHORIZED_ACTION);
		}

		// 学習状況を設定する
		ProgressLog log = new ProgressLog();
		log.setGoal(goal);
		log.setDate(request.getDate());
		log.setDescription(request.getDescription());
		log.setHoursSpent(request.getHoursSpent());

		// 学習状況を登録する
		ProgressLog saved = logService.addProgress(log);

		// 学習状況を保存する
		ProgressLogDto dto = new ProgressLogDto(
				saved.getId(),
				saved.getDate(),
				saved.getDescription(),
				saved.getHoursSpent());

		// 保存された学習進捗情報を返却する
		return ResponseEntity.ok(dto);
	}

	/**
	 * 自分の目標の記録のみ取得する
	 */
	@GetMapping("/goal/{goalId}")
	public ResponseEntity<List<ProgressLogDto>> getLogs(@PathVariable Long goalId) {

		// ログイン認証済みユーザーのユーザー名を取得する
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// ユーザ名を検索し、ユーザー情報を取得する。存在しない場合、例外をスローする。
		User user = userService.findByUsernameOrThrow(username);

		// 対象の目標がログインユーザーのものであることを検証する
		LearningGoal goal = goalService.findByIdOrThrow(goalId);

		// 学習目標のIDが認証済みユーザーのIDと一致しない場合
		if (!Objects.equals(goal.getUser().getId(), user.getId())) {

			// 例外をスローする
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		// DTOに変換（エンティティを直接返さない）
		List<ProgressLogDto> result = logService.getLogsByGoalId(goalId)
				.stream()
				.map(log -> new ProgressLogDto(
						log.getId(),
						log.getDate(),
						log.getDescription(),
						log.getHoursSpent()))
				.collect(Collectors.toList());

		// 学習進捗一覧を返却する
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{logId}")
	public ResponseEntity<Void> deleteLog(@PathVariable Long logId) {

		// サービス層の処理を実行し、対象の学習進捗情報を消去する
		logService.deleteLog(logId);

		// 「204 No Content」データなし、成功を返却する
		return ResponseEntity.noContent().build();
	}
}
