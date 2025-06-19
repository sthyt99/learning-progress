package com.example.learning_progress.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.learning_progress.entity.User;
import com.example.learning_progress.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * ユーザー名検索処理
	 * 
	 * @param username ユーザー名
	 * @return 指定されたユーザー名に一致するユーザー名を返却する
	 */
	public Optional<User> findByUsername(String username) {

		// ユーザー名を取得する
		return userRepository.findByUsername(username);
	}

	/**
	 * ユーザー登録処理
	 * 
	 * @param user ユーザー情報
	 * @return ユーザー情報を保存する
	 */
	public User registerUser(User user) {

		// パスワードをハッシュ化して保存する
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// ユーザーエンティティをデータベースへ登録する
		return userRepository.save(user);
	}
}
