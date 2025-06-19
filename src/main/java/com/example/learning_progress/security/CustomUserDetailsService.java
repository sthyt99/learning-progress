package com.example.learning_progress.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.learning_progress.entity.User;
import com.example.learning_progress.repository.UserRepository;

/**
 * ユーザー名からユーザー情報を取得して認証に使える形に変換する
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// DBからユーザー情報を取得
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found")); // 見つからない場合、例外スロー

		// ユーザー情報を保存したクラスのインスタンスを作成する
		return new UserDetailsImpl(user);
	}
}
