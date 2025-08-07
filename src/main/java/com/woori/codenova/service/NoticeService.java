package com.woori.codenova.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Category;
import com.woori.codenova.entity.Notice;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.NoticeRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeService {
	// 초기에 시스템 관리자 정보가 저장되어야 하는게 아닐까??

	private final NoticeRepository noticeRepository;

	// 목록 - 페이징 - 검색

	// 조회 - 상세

	// 등록

	// 수정

	// 삭제

	// 검색
	private Specification<Notice> search(String kw) {
		return new Specification<>() {

			private static final long seriaVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Notice> r, CriteriaQuery<?> q, CriteriaBuilder cb) {

				q.distinct(true); // 중복을 제거

				Join<Notice, SiteUser> u = r.join("author", JoinType.LEFT);// 공지와 작성자
				Join<Notice, Category> c = r.join("category", JoinType.LEFT);// 공지와 게시판

				// 제목, 내용, 작성자ID
				return cb.or(cb.like(r.get("subject"), "%" + kw + "%"), cb.like(r.get("content"), "%" + kw + "%"),
						cb.like(u.get("userId"), "%" + kw + "%"));
			}
		};
	}
}
