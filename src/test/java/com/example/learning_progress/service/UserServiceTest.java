package com.example.learning_progress.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.learning_progress.entity.User;
import com.example.learning_progress.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * ユーザーサービス層のテストクラス
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserService userService;
	
	/**
	 * 成功：ユーザー名取得処理
	 */
	@Test
	void  testFindByUsername_Success() {
		
		User user = new User();
		user.setUsername("allice");
		
		// 存在するユーザーを取得する（成功パターン）
		when(userRepository.findByUsername("allice")).thenReturn(Optional.of(user));
		
		User result = userService.findByUsernameOrThrow("allice");
		
		// 戻り値が期待通りかチェックする
		assertEquals("allice", result.getUsername());
	}
	
	/**
	 * 失敗：ユーザー名取得処理
	 */
	@Test
	void testFindByUsername_NotFound() {
		when(userRepository.findByUsername("bob")).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> {
			userService.findByUsernameOrThrow("bob");
		});
	}
}
