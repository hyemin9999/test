package com.woori.codenova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.woori.codenova.entity.Category;
import com.woori.codenova.entity.Role;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.CategoryRepository;
import com.woori.codenova.repository.RoleRepository;
import com.woori.codenova.repository.UserRepository;
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

//	@Test
	void contextLoads() {

	}

//	@Test
//	@Transactional
	void createUser() {

		SiteUser u2 = new SiteUser();
		u2.setUsername("user1");
		u2.setPassword(passwordEncoder.encode("1234"));
		u2.setEmail("user1@email.com");
		u2.setCreateDate(LocalDateTime.now());
		userReporitory.save(u2);

		// 일반사용자
		Role ritem = roleReporitory.findByGrade(0).orElse(null);
		assertTrue(ritem != null);

		u2 = userReporitory.findByUsername("user1").orElse(null);
		assertTrue(u2 != null);

		if (ritem != null) {
			// Role : grade - 일반사용자(0), 슈퍼관리자(1) - 고정.수정삭제불가
			u2.getAuthority().add(ritem);
			userReporitory.save(u2);
		}

//		this.userService.create("user1", "1234", "user1@email.com");

	}

//	@Test
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

//	@Test
	void RolesTest() {
		Role r11 = roleService.getItem(1);// 사용자
		Role r22 = roleService.getItem(2);// 관리자

		assertEquals("사용자", r11.getName());
		assertEquals("관리자", r22.getName());
	}

//	@Test
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

//	@Test
	void UsersTest() {

		SiteUser u11 = userService.getItem("admin");
		SiteUser u22 = userService.getItem("user");

		assertEquals(1, u11.getId());
		assertEquals(2, u22.getId());
	}

//	@Test
//	@Transactional
	void insertAuthoritys() {

//		u1.getAuthority().add(r2);
//		userReporitory.save(u1);
//
//		u2.getAuthority().add(r1);
//		userReporitory.save(u2);
	}

//	@AfterEach
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
