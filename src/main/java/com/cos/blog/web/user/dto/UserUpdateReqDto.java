package com.cos.blog.web.user.dto;

import lombok.Data;

@Data
public class UserUpdateReqDto {
	private String password;
	private String email;
}
