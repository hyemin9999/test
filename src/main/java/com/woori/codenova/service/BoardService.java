package com.woori.codenova.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Board;
import com.woori.codenova.entity.Comment;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.BoardRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	// 초기에 시스템 관리자 정보가 저장되어야 하는게 아닐까??

	private final BoardRepository boardRepository;

	// 목록 - 페이징 - 검색

	// 조회 - 상세

	// 등록

	// 수정

	// 삭제

	// 검색
	private Specification<Board> search(String kw) {
		return new Specification<>() {

			private static final long seriaVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Board> r, CriteriaQuery<?> q, CriteriaBuilder cb) {

				q.distinct(true); // 중복을 제거

				Join<Board, SiteUser> u = r.join("author", JoinType.LEFT);// 게시글과 작성자
				Join<Board, Comment> c = r.join("comment", JoinType.LEFT);// 게시글과 댓글
				Join<Board, SiteUser> u1 = r.join("author", JoinType.LEFT);// 댓글과 작성자

				// 제목, 내용, 작성자ID, 댓글(내용, 작성자ID)
				return cb.or(cb.like(r.get("subject"), "%" + kw + "%"), // 게시글 제목
						cb.like(r.get("content"), "%" + kw + "%"), // 댓글 내용
						cb.like(u.get("userId"), "%" + kw + "%"), // 게시글 작성자
						cb.like(c.get("content"), "%" + kw + "%"), // 댓글 내용
						cb.like(u1.get("userId"), "%" + kw + "%")); // 댓글 작성자
			}
		};
	}
}
