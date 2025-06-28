package com.example.learning_progress;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.learning_progress.entity.LearningGoal;
import com.example.learning_progress.entity.ProgressLog;
import com.example.learning_progress.entity.User;
import com.example.learning_progress.repository.LearningGoalRepository;
import com.example.learning_progress.repository.ProgressLogRepository;
import com.example.learning_progress.repository.UserRepository;

/**
 * Entityクラスの初期データ登録（開発・テスト用）
 */
@Configuration
public class DataInitializer {

	@Bean
	public CommandLineRunner initData(
			UserRepository userRepository,
			LearningGoalRepository goalRepository,
			ProgressLogRepository logRepository,
			PasswordEncoder passwordEncoder) {

		// 各Entityクラスのフィールドに初期値を設定する
		return args -> {

			// ① 全データ削除（順序に注意：子→親）
			// ※本番環境では削除
			logRepository.deleteAll();
			goalRepository.deleteAll();
			userRepository.deleteAll();

			// ユーザーチェック
			Optional<User> existing = userRepository.findByUsername("alice");

			Optional<User> existingAdmin = userRepository.findByUsername("admin");

			// ADMINユーザーがいない場合
			if (existingAdmin.isEmpty()) {

				// ADMINユーザー作成
				User admin = new User();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("adminpass"));
				admin.setRoles(Set.of("ADMIN"));
				userRepository.save(admin);
			}

			// ユーザーが存在しない場合
			if (existing.isEmpty()) {

				// ユーザー作成（パスワードはハッシュ化）
				User alice = new User();
				alice.setUsername("alice");
				alice.setPassword(passwordEncoder.encode("password123"));
				alice.setRoles(Set.of("USER"));
				userRepository.save(alice);

				// 学習目標作成
				LearningGoal goal = new LearningGoal();
				goal.setTitle("Java基礎習得");
				goal.setTargetHours(20);
				goal.setUser(alice);
				goal.setCreatedAt(java.time.LocalDateTime.now());
				goalRepository.save(goal);

				// 学習記録追加
				ProgressLog log1 = new ProgressLog();
				log1.setGoal(goal);
				log1.setDate(LocalDate.now().minusDays(1));
				log1.setDescription("Javaのfor文を学習");
				log1.setHoursSpent(1.5);
				logRepository.save(log1);

				// 学習記録追加2
				ProgressLog log2 = new ProgressLog();
				log2.setGoal(goal);
				log2.setDate(LocalDate.now());
				log2.setDescription("Spring Boot入門を読んだ");
				log2.setHoursSpent(2.0);
				logRepository.save(log2);

				// コンソールに出力する
				System.out.println("✅ 初期データを登録しました");
			}
		};
	}
}
