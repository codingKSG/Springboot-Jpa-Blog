package com.cos.blog.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.config.auth.PrincipalDetails;

@Controller // ViewResolver
public class UserController {
	
	// user/1 -> user정보 가져오기
	// 인증관련 -> AuthController
	
	@GetMapping("/user")
	public @ResponseBody String findAll(@AuthenticationPrincipal PrincipalDetails  principalDetails ) {
		// @Controller + @ResponseBody = @RestController
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
//		PrincipalDetails  principalDetails = (PrincipalDetails)authentication.getPrincipal();
//		System.out.println("details: "+principalDetails.getUser());
		System.out.println("principalDetails.getUsername: "+principalDetails.getUsername());
		
		return "User";
	}
	
}