package com.woori.codenova;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
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

@SpringBootTest
class CodenovaApplicationTests {

	@Autowired
	private RoleRepository roleReporitory;
//	@Autowired
//	private RoleService roleService;

	@Autowired
	private UserRepository userReporitory;
//	@Autowired
//	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CategoryRepository categoryRepository;

	// @Test
	void contextLoads() {

	}

	// @Test
	void insertRoles() {
		// 역할 초기값 ==> 일반사용자(0), 관리자(1) insert
		Role r1 = new Role();
		r1.setName("사용자");
		r1.setGrade(0);
		r1.setCreateDate(LocalDateTime.now());
		roleReporitory.save(r1);

		Role r2 = new Role();
		r2.setName("관리자");
		r2.setGrade(1);
		r2.setCreateDate(LocalDateTime.now());
		roleReporitory.save(r2);
	}

	// @Test
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
//	@Transactional
	void insertAuthoritys() {

		SiteUser u1 = userReporitory.findByUsername("admin").orElse(null);
		SiteUser u2 = userReporitory.findByUsername("user").orElse(null);

		assertEquals(1, u1.getId());
		assertEquals(2, u2.getId());

		Role r1 = roleReporitory.findById(1).orElse(null);// 사용자
		Role r2 = roleReporitory.findById(2).orElse(null);// 관리자

		assertEquals("사용자", r1.getName());
		assertEquals("관리자", r2.getName());

		u1.getAuthority().add(r2);
		userReporitory.save(u1);

		u2.getAuthority().add(r1);
		userReporitory.save(u2);
	}

	@AfterEach
	void tearDown() {
		SiteUser u1 = userReporitory.findByUsername("admin").orElse(null);
		SiteUser u2 = userReporitory.findByUsername("user").orElse(null);

		assertEquals(1, u1.getId());
		assertEquals(2, u2.getId());

		u1.getAuthority().clear();
		u2.getAuthority().clear();
	}

//	@Test
	void insertCategory() {
		// 게시판 초기값 - 공지사항
		Category c1 = new Category();
		c1.setName("공지사항");
		c1.setCreateDate(LocalDateTime.now());
		categoryRepository.save(c1);

		// 자유게시판
		Category c2 = new Category();
		c2.setName("자유게시판");
		c2.setCreateDate(LocalDateTime.now());
		categoryRepository.save(c2);
	}
}
