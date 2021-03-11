package com.cos.blog.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration // IoC등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// IoC 등록만 하면 AuthenticationManager가 BCrypt로 자동 검증해줌.
	@Bean
	public BCryptPasswordEncoder encoded() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		/*
		 * csrf()
		 */
		http.authorizeRequests().antMatchers("/user/**", "/post/**")
				.access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				// ROLE_ 는 강제성이 있음. 롤 검증시
				.antMatchers("/admin").access("hasRole('ROLE_ADMIN')").anyRequest().permitAll().and().formLogin() // x-www-form-urlencoded
				.loginPage("/loginForm").loginProcessingUrl("/login")
				// SpringSecurity가 Post 메서드 방식의 /login이 들어오면 낚아챔
				// x-www-form-urlencoded로 데이터가 들어왔다고 가정하고 파싱함
				// key값(name)이 username, password가 아닐 경우 파싱을 못함
				// username + password 를 이용해 객체 생성 (UsernamePasswordAuthenticationToken)
				// AuthenticationManager(목표 : 로그인하기) 객체에게 전달
				// UserDetailsService라는 객체의 loadByUsername(String username) 함수를 실행함.
				// loadByUsername 함수를 오버라이딩해야함. (개발자에게 위임)
				// userRepository.findByUsername(username); => 유저 있는지 검증
				// AuthenticationManager에게 return 해줘야함. User 객체 (문제 있음)
				// UserDetails 리턴 -> AuthenticationManger가 그 값을 Authentication 객체로 변환
				// session 영역에 securityContextHolder 키 값 = 저장할 수 있는 값은 UserDetails 타입(강제)
				// securitySession가 분석하기 쉬운 객체로 묶기위해
				// 각 프로젝트 마다 User의 정보가 다르기 때문에 하나의 타입으로 통일성을 줌

				.defaultSuccessUrl("/")
				// login이 성공하면 가는 주소지
				// 가려고 하는 경로가 없을 경우 현 주소지로 감
//				.successHandler(new AuthenticationSuccessHandler() {
//				
//					@Override
//					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//						Authentication authentication) throws IOException, ServletException {
//
//					response.sendRedirect("/");
//					
//					}
//				})
				;
	}
}
