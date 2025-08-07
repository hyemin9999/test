package com.woori.codenova.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 내용
	@Column(columnDefinition = "TEXT")
	private String contents;

	// 작성날짜
	private LocalDateTime createDate;
	// 수정날짜
	private LocalDateTime modifyDate;

	// 삭제여부
	private Boolean isDelete;
	// 삭제 날짜
	private LocalDateTime deleteDate;

	// 작성자

	// 게시글
	@ManyToOne
	private Board board;

	// 추천

	// 북마크 (즐겨찾기)
}
