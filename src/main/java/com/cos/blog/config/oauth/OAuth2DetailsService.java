package com.cos.blog.config.oauth;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.blog.config.auth.PrincipalDetails;
import com.cos.blog.domain.user.RoleType;
import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

// OAuth 순서 2
@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("OAuth 로그인 진행중.......");
		System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());

		// 1. Access Token으로 회원정보를 받았다는 의미
		OAuth2User oAuth2User = super.loadUser(userRequest);

		// 레트로핏
		System.out.println("oauth2User: " + oAuth2User.getAttributes());
		return processOAuth2User(userRequest, oAuth2User);
	}

	// 구글 로그인 프로세스
	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		// 1. 통합 클래스를 생성
		// 각 Resource Server에서 던지는 Attribute 정보가 다르기 때문
		System.out.println("로그인 된 곳은? " + userRequest.getClientRegistration().getClientName());

		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getClientName().equals("Google")) {
			oAuth2UserInfo = new GoogleInfo(oAuth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getClientName().equals("Facebook")) {
			oAuth2UserInfo = new FacebookInfo(oAuth2User.getAttributes());
		}
		
		// 2. 최초 o: 회원가입 + 로그인, 최초 x: 로그인
		User userEntity = userRepository.findByUsername(oAuth2UserInfo.getUsername());
		
		UUID uuid = UUID.randomUUID();
		String endPassword = new BCryptPasswordEncoder().encode(uuid.toString());
		
		if(userEntity == null) {
			User user = User.builder()
					.username(oAuth2UserInfo.getUsername())
					.password(endPassword)
					.email(oAuth2UserInfo.getEmail())
					.role(RoleType.USER)
					.build();
			userEntity = userRepository.save(user);
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		} else { // 이미 회원가입이 완료 됬다는 뜻(원래는 구글정보가 변경될 수 있기 때문에 Update를 쳐줘야함 - 나중 구현)
			// Update 로직 들어가야함
			
			return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
		}
	}
}
