package com.example.learning_progress.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learning_progress.security.JwtUtil;

/**
 * JWTベースのログイン処理（認証）を提供する
 * ログイン成功時に JWT トークンを返す
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	/**
	 * Mapキーのユーザー名
	 */
	private static final String KEY_USERNAME = "username";

	/**
	 * Mapキーのパスワード
	 */
	private static final String KEY_PASSWORD = "password";

	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;

	public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {

		// ユーザー名
		String username = body.get(KEY_USERNAME);
		// パスワード
		String password = body.get(KEY_PASSWORD);

		// ユーザーを検索し、パスワードを検証する
		authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		// 署名付き JWT トークンを発行する
		String token = jwtUtil.generateToken(username);

		// 成功時、200 OK
		return ResponseEntity.ok(Map.of("token", token));
	}
}
