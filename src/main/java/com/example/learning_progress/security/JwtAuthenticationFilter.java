package com.example.learning_progress.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * すべてのHTTPリクエストに対して一度だけ実行
 * HTTPリクエストの Authorization ヘッダーから JWT を取り出す
 * JWT を検証し、ユーザー情報を SecurityContext に設定する
 * Spring Security がログイン済みと判断する
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * HTTPリクエストの認証ヘッダー名（標準）
	 */
	private static final String AUTH_HEADER = "Authorization";

	/**
	 * トークンの先頭に付ける接頭辞
	 */
	private static final String TOKEN_PREFIX = "Bearer ";

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		// トークン部分のみ取り出す
		String authHeader = request.getHeader(AUTH_HEADER);

		if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
			String token = authHeader.substring(TOKEN_PREFIX.length());

			// トークンからユーザー名を取得する
			String username = jwtUtil.extractUsername(token);

			// 認証済みでない場合
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				// ユーザー情報を取得する
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// トークンの検証が成功の場合
				if (jwtUtil.validateToken(token)) {

					// トークンを生成する
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());

					// セキュリティコンテキストに設定する（ログイン済み）
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		
		// トークンが無いまたは、不正な形式の場合
		if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
			
			// チェーン内の次のフィルターやコントローラへリクエストを渡す
		    filterChain.doFilter(request, response);
		    return;
		}
	}
}
