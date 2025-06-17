package com.example.learning_progress.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.learning_progress.entity.User;
import com.example.learning_progress.repository.UserRepository;

@Service
public class UserService {

	/**
	 * ユーザーリポジトリ
	 */
	private final UserRepository userRepository;

	/**
	 * コンストラクタ
	 * @param userRepository ユーザーリポジトリ
	 */
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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

		// ユーザーエンティティをデータベースへ登録する
		return userRepository.save(user);
	}
}
