package com.example.learning_progress.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning_progress.entity.User;
import com.example.learning_progress.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	/**
	 * ユーザーサービス
	 */
	private final UserService userService;

	/**
	 * コンストラクタ
	 * @param userService ユーザーサービス
	 */
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<User> register(@RequestBody User user) {

		// サービス層の保存処理を実行し、保存したユーザー情報を取得する
		User saved = userService.registerUser(user);

		// 保存されたユーザー情報を返却する
		return ResponseEntity.ok(saved);
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getByUsername(@PathVariable String username) {

		// サービス層の処理を実行し、ユーザー名からユーザー情報を取得する
		User user = userService.findByUsernameOrThrow(username);

		// 存在する場合「200 OK」、存在しない場合「404 NOT FOUND」を返却する
		return ResponseEntity.ok(user);
	}
}
