package com.woori.codenova.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	@Column(nullable = false)
	private String subject;

	// 내용
	@Column(columnDefinition = "TEXT", nullable = false)
	private String contents;

	// 조회수
	@Column(nullable = false)
	@ColumnDefault("0")
	private Integer viewCount;

	// 작성날짜
	@Column(nullable = false)
	private LocalDateTime createDate;
	// 수정날짜
	private LocalDateTime modifyDate;

	// 삭제여부
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean isDelete;
	// 삭제 날짜
	private LocalDateTime deleteDate;

	// 게시글 삭제시 연결되어 있는 댓글에 의해서 작성자는 사라져도 될듯?
	// @ManyToOne(optional = false, fetch = FetchType.EAGER)
	// 작성자
	@ManyToOne
	@JoinColumn(name = "userId")
	private SiteUser author;

	// 게시판(게시글 분류)
	@ManyToOne
	@JoinColumn(name = "categoryId", nullable = false)
	private Category category;

	// 댓글
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Comment> commentList;

	// 추천
	@ManyToMany
	Set<SiteUser> voter;

	// 즐겨찾기
	@ManyToMany
	Set<SiteUser> favorites;

}
