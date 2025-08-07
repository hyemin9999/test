package com.woori.codenova.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {

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

	// 삭제여부
	private Boolean isDelete;
	// 삭제 날짜
	private LocalDateTime deleteDate;

	// 작성자

	// 게시판(게시글 분류)
	@ManyToOne
	private Category categoty;

	// 댓글
	@OneToMany
	private List<Comment> commentList;

	// 추천

	// 북마크 (즐겨찾기)

}
