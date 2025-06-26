package com.example.learning_progress.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.learning_progress.entity.User;
import com.example.learning_progress.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

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
	public User findByUsernameOrThrow(String username) {

		// ユーザー名を取得する
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("ユーザーが見つかりません"));
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

		// デフォルトで"USER"を設定する
		user.setRoles(Set.of("USER"));

		// ユーザーエンティティをデータベースへ登録する
		return userRepository.save(user);
	}

	/**
	 * 
	 * 全ユーザー情報取得処理（管理者用）
	 * 
	 * @return ユーザー情報をすべて返却する
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
