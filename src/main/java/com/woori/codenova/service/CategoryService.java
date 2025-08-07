package com.woori.codenova.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Category;
import com.woori.codenova.repository.CategoryRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
	// 초기에 시스템 관리자 정보가 저장되어야 하는게 아닐까??

	private final CategoryRepository categoryRepository;

	// 목록 - 페이징 - 검색

	// 조회 - 상세

	// 등록

	// 수정

	// 삭제

	// 검색
	private Specification<Category> search(String kw) {
		return new Specification<>() {

			private static final long seriaVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Category> r, CriteriaQuery<?> q, CriteriaBuilder cb) {

				q.distinct(true); // 중복을 제거

				// 게시판 이름
				return cb.or(cb.like(r.get("name"), "%" + kw + "%"));
			}
		};
	}
}
