package com.woori.codenova.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 제목
	private String subject;

	// 내용
	@Column(columnDefinition = "TEXT")
	private String contents;

	// 조회수
	private Integer viewCount;

	// 작성날짜
	private LocalDateTime createDate;
	// 수정날짜
	private LocalDateTime modifyDate;

	// 작성자

	// 게시판(게시글 분류)

}
