package com.cos.blog.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.config.auth.PrincipalDetails;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.UserService;
import com.cos.blog.web.dto.CommonRespDto;
import com.cos.blog.web.user.dto.UserUpdateReqDto;

import lombok.RequiredArgsConstructor;

@Controller // ViewResolver
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// user/1 -> user정보 가져오기
	// 인증관련 -> AuthController

	@GetMapping("/user/{id}")
	public String updateForm(@PathVariable Integer id, Model model) {
		model.addAttribute("id", id);
		return "user/updateForm";
	}

	@PutMapping("/user/{id}")
	public @ResponseBody CommonRespDto<?> update(@PathVariable int id, @RequestBody UserUpdateReqDto userUpdateReqDto,
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("받은 데이터: " + userUpdateReqDto);

		User userEntity = userService.회원수정(id, userUpdateReqDto);

		// 세션 변경
		// UsernamePasswordToken -> Authentication 객체로 만들어서 -> security 컨텍스트 홀데에 집어넣기
		principalDetails.setUser(userEntity);

//		Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(),
//				userEntity.getPassword());
//		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new CommonRespDto<>(1, null);
	}

	@GetMapping("/user")
	public @ResponseBody String findAll(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		// @Controller + @ResponseBody = @RestController

//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
//		PrincipalDetails  principalDetails = (PrincipalDetails)authentication.getPrincipal();
//		System.out.println("details: "+principalDetails.getUser());
		System.out.println("principalDetails.getUsername: " + principalDetails.getUsername());

		return "User";
	}

}