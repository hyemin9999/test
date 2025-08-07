package com.woori.codenova.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 사용자 ID
	@Column(unique = true) // 중복불가
	private String userId;

	// 비밀번호
	private String password;

	@Email
	@Column(unique = true) // 중복불가
	private String email;

	// 가입날짜
	private LocalDateTime createDate;
	// 수정날짜
	private LocalDateTime modifyDate;

//	// 일반 사용자 여부(false=관리자)
//	private Boolean isUser;
}
