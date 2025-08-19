package com.woori.codenova;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.woori.codenova.entity.Category;
import com.woori.codenova.entity.Role;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.CategoryRepository;
import com.woori.codenova.repository.RoleRepository;
import com.woori.codenova.repository.UserRepository;
import com.woori.codenova.service.CategoryService;
import com.woori.codenova.service.RoleService;
import com.woori.codenova.service.UserService;

@SpringBootTest
class CodenovaApplicationTests {

	@Autowired
	private RoleRepository roleReporitory;
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRepository userReporitory;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryService categoryService;

//	@Test
	void contextLoads() {

	}

	@Test
	void insertRoles() {
		// 역할 초기값 ==> 슈퍼 관리자(1) insert

		Role r2 = new Role();
		r2.setName("관리자");
		r2.setGrade(1);
		r2.setCreateDate(LocalDateTime.now());
		roleReporitory.save(r2);

		Role r3 = new Role();
		r3.setName("매니저");
		r3.setGrade(2);
		r3.setCreateDate(LocalDateTime.now());
		roleReporitory.save(r3);
	}

	@Test
	void insertUsers() {
		// 사용자 초기값 ==> 사용자(user), 관리자(admin) insert
		SiteUser u1 = new SiteUser();
		u1.setUsername("admin");
		u1.setPassword(passwordEncoder.encode("1234"));
		u1.setEmail("admin@email.com");
		u1.setCreateDate(LocalDateTime.now());
		userReporitory.save(u1);

		SiteUser u2 = new SiteUser();
		u2.setUsername("user");
		u2.setPassword(passwordEncoder.encode("1234"));
		u2.setEmail("user@email.com");
		u2.setCreateDate(LocalDateTime.now());
		userReporitory.save(u2);
	}

	@Test
	void insertCategory() {
		// 게시판 초기값 - 공지사항
//		Category c1 = new Category();
//		c1.setName("공지사항");
//		c1.setCreateDate(LocalDateTime.now());
//		categoryRepository.save(c1);

		// 자유게시판
		Category c2 = new Category();
		c2.setName("자유게시판");
		c2.setCreateDate(LocalDateTime.now());
		categoryRepository.save(c2);
	}

}
