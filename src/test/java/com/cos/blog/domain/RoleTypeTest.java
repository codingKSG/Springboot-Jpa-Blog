package com.cos.blog.domain;

import org.junit.jupiter.api.Test;

import com.cos.blog.domain.user.RoleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleTypeTest {
	
	@Test
	public void roll_값검증() {
		log.debug("1 : "+RoleType.ADMIN);
		log.debug("2 : "+RoleType.USER);
		log.debug("3 : "+RoleType.ADMIN.name());
		log.debug("4 : "+RoleType.ADMIN.ordinal());
		log.debug("5 : "+RoleType.values());
	}
}
