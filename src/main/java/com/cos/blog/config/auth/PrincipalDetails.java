package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.blog.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private User user;
	private Map<String, Object> attribytes; // OAuth 제공자로 부터 받은 회원 정보
	private boolean isOAuth;

	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	public PrincipalDetails(User user, Map<String, Object> attribytes) {
		this.user = user;
		this.attribytes = attribytes;
		this.isOAuth = true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attribytes;
	}

	@Override
	public String getName() {
		return "몰러~ ";
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 해당 계정 만료
	@Override
	public boolean isAccountNonExpired() {
		// 해당 계정이 만료 안됬는지
		return true;
	}

	// 로그인 반복 실패시 잠금
	@Override
	public boolean isAccountNonLocked() {

		// 잠금되지 않았는지
		return true;
	}

	// 비밀번호 만료
	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호 만료 안됬는지
		return true;
	}

	// 계정 활성화
	@Override
	public boolean isEnabled() {
		// 계정 활성화 됬는지
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collecters = new ArrayList<>();
		collecters.add(() -> "ROLE_" + user.getRole().name());
		return collecters;
	}

}
