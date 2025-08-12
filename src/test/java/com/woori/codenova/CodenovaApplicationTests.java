package com.woori.codenova;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.woori.codenova.entity.Category;
import com.woori.codenova.repository.CategoryRepository;
import com.woori.codenova.repository.RoleRepository;
import com.woori.codenova.repository.UserRepository;

@SpringBootTest
class CodenovaApplicationTests {

	@Autowired
	private RoleRepository roleReporitory;
	@Autowired
	private UserRepository userReporitory;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {

		// 역할 초기값 ==> 일반사용자(0), 관리자(1) insert

//		Role r1 = new Role();
//		r1.setName("사용자");
//		r1.setGrade(0);
//		r1.setCreateDate(LocalDateTime.now());
//		roleReporitory.save(r1);
//
//		Role r2 = new Role();
//		r2.setName("관리자");
//		r2.setGrade(1);
//		r2.setCreateDate(LocalDateTime.now());
//		roleReporitory.save(r2);

//		SiteUser u1 = new SiteUser();
//		u1.setUsername("admin");
//		u1.setPassword(passwordEncoder.encode("1234"));
//		u1.setEmail("admin@email.com");
//		u1.setCreateDate(LocalDateTime.now());
//		userReporitory.save(u1);

//		u1.getAuthority().add(r1);
//		userReporitory.save(u1);

		// 게시판 초기값 - 자유게시판
		Category c1 = new Category();
		c1.setName("자유게시판");
		c1.setCreateDate(LocalDateTime.now());
		categoryRepository.save(c1);

		// 공지사항
		Category c2 = new Category();
		c2.setName("공지사항");
		c2.setCreateDate(LocalDateTime.now());
		categoryRepository.save(c2);

	}

}
