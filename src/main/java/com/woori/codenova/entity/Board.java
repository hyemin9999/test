package com.woori.codenova.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	private boolean isDelete;
	// 삭제 날짜
	private LocalDateTime deleteDate;

	// 작성자
	@ManyToOne
	private SiteUser author;

	// 게시판(게시글 분류)
	@ManyToOne
	private Category category;

	// 댓글
	@OneToMany(mappedBy = "board")
	private List<Comment> commentList;

	// 추천
	@ManyToMany
	Set<SiteUser> voter;

	// 북마크 (즐겨찾기)

}
