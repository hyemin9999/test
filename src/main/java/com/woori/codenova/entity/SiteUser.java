package com.woori.codenova.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 사용자 ID
	@Column(unique = true, nullable = false) // 중복불가
	private String username;

	// 비밀번호
	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false) // 중복불가
	private String email;

	// 가입날짜
	@Column(nullable = false)
	private LocalDateTime createDate;
	// 수정날짜
	private LocalDateTime modifyDate;

//	// 일반 사용자 여부(false=관리자)
//	private Boolean isUser;

	// 권한
	@ManyToMany
	@JoinTable(name = "userAuthority", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	Set<Role> authority;// = new HashSet<>();
}
