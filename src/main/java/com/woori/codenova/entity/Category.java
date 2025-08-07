package com.woori.codenova.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 게시판 분류 이름
	@Column(length = 50)
	private String name;

	// 등록날짜
	private LocalDateTime createDate;
	// 수정날짜
	private LocalDateTime modifyDate;

	// 게시글 == board
	@OneToMany(mappedBy = "category")
	private List<Board> boardList;

	// 공지사항 - 게시글
	@OneToMany(mappedBy = "category")
	private List<Notice> noticeList;
}
