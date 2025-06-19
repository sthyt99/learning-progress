package com.example.learning_progress.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.learning_progress.security.CustomUserDetailsService;
import com.example.learning_progress.security.JwtAuthenticationFilter;

/**
 * JWTを使った認証構成を定義するためのセキュリティを設定
 * 認証方式：JWT
 * 認可ルール：/api/auth/** や /api/users は認証不要、それ以外は保護
 * 認証処理：DBから取得（CustomUserDetailsService） + パスワードは BCrypt
 */
@Configuration
@EnableMethodSecurity // メソッドレベルの認可を有効化
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtFilter;
	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(JwtAuthenticationFilter jwtFilter, CustomUserDetailsService userDetailsService) {
		this.jwtFilter = jwtFilter;
		this.userDetailsService = userDetailsService;
	}

	/**
	 * HTTPリクエストに対するセキュリティ設定（認可・CSRF・セッションなど）を定義する。
	 *
	 * @param http HttpSecurity オブジェクト
	 * @return セキュリティフィルターチェーン
	 * @throws Exception セキュリティ構成中の例外
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http
				.csrf(csrf -> csrf.disable()) // CSRF不要のため無効化
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/**", "/api/users").permitAll() // 指定パスの認証を不要
						.anyRequest().authenticated() // それ以外は認証が必要
				)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // トークンを前提とし、セッションを使用しない
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // JWTフィルターを認証フィルターの前に追加
				.userDetailsService(userDetailsService) // 認証時に使うユーザー情報取得方法を設定
				.build();
	}

	/**
	 * 認証マネージャーの設定。ユーザー情報とパスワードエンコーダーを使用する。
	 *
	 * @param http HttpSecurity オブジェクト
	 * @return AuthenticationManager 認証マネージャー
	 * @throws Exception 設定エラー
	 */
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {

		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);

		builder.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());

		return builder.build();
	}

	/**
	* パスワードエンコーダーの定義。
	* ユーザーのパスワードは BCrypt アルゴリズムでハッシュ化される。
	*
	* @return PasswordEncoder パスワードエンコーダー
	*/
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
