package com.woori.codenova.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	@Column(columnDefinition = "TEXT", nullable = false)
	private String contents;

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

	// optional = false 작성자가 null일수 없다.
	// @ManyToOne(optional = false, fetch = FetchType.EAGER)
	// 작성자
	@ManyToOne // (fetch = FetchType.EAGER) // 댓글 삭제시 작성자 정보가 없어도 될거같아서.
	@JoinColumn(name = "userId")
	private SiteUser author;

	// 게시글
	// @ManyToOne(optional = false, fetch = FetchType.EAGER)
	@ManyToOne
	@JoinColumn(name = "boardId", nullable = false)
	private Board board;

	// 추천
	@ManyToMany
	@JoinTable(name = "commentVoter", joinColumns = @JoinColumn(name = "commentId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	Set<SiteUser> voter;

	// 즐겨찾기
	@ManyToMany
	@JoinTable(name = "commentFavorite", joinColumns = @JoinColumn(name = "commentId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	Set<SiteUser> favorites;
}
