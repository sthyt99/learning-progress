package com.example.learning_progress.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.learning_progress.entity.User;

/**
 * UserDeatilsインターフェースの実現クラス
 */
public class UserDetailsImpl implements UserDetails {

	private final User user;

	public UserDetailsImpl(User user) {
		this.user = user;
	}

	/**
	 * ユーザーのロールを返却する
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toList());
	}

	/**
	 * DBのユーザー名
	 */
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/**
	 * 暗号化されたパスワード
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	/**
	 * 常に有効として扱う
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 常にロックされていない
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * パスワードの期限切れなし
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 常に有効
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}
