package com.example.learning_progress.util;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * JWT（JSON Web Token）の生成・解析・検証
 */
@Component
public class JwtUtil {

	// 有効期限 : 24時間
	private static final long EXPIRATION_MS = Duration.ofDays(1).toMillis();

	// 秘密鍵の文字列(32文字以上)
	private final String secret = "your-256-bit-secret-your-256-bit-secret";
	// Key 型に変換し、署名用に利用
	private final Key secretKey = Keys.hmacShaKeyFor(secret.getBytes());

	/**
	 * ユーザー名からJWTトークンを生成する
	 */
	public String generateToken(String username) {

		// ユーザー名、発行日時、有効期限、署名をJWT文字列に変換し、返却する
		return Jwts.builder() // JWT を生成（発行）するためのビルダーを取得
				.setSubject(username) // トークンの対象（ユーザー名）
				.setIssuedAt(new Date()) // 発行日時
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // 24時間有効
				.signWith(secretKey, SignatureAlgorithm.HS256) // 署名
				.compact(); // JWT文字列に変換
	}

	/**
	 * トークンからユーザー名を抽出する
	 */
	public String extractUsername(String token) {

		// ユーザー名を取得する
		return this.parseClaims(token).getSubject();
	}

	/**
	 * トークンが有効かどうかを検証する
	 */
	public boolean validateToken(String token) {

		try {

			// ユーザー名を取得する
			extractUsername(token);

			// 結果を返却する
			return true;

			// 取得できない場合
		} catch (JwtException | IllegalArgumentException e) {

			// 結果を返却する
			return false;
		}
	}

	/**
	* トークンをパースしてClaims（中身）を返す
	*/
	private Claims parseClaims(String token) {

		return Jwts.parserBuilder() // JWT を解析（パース）するためのビルダーを取得
				.setSigningKey(secretKey) // 検証に使用するキー
				.build() // 上記キーをもとにインスタンス生成
				.parseClaimsJws(token) // トークンを解析
				.getBody(); // ペイロード（トークンの中身）を取得
	}
}
