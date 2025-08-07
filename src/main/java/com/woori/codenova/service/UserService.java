package com.woori.codenova.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.UserReporitory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	// 초기에 시스템 관리자 정보가 저장되어야 하는게 아닐까??

	private final UserReporitory userReporitory;

	// 목록 - 페이징 - 검색
	public Page<SiteUser> getList(int page, String kw) {

		// 정렬
		List<Sort.Order> solist = new ArrayList<>();
		solist.add(Sort.Order.desc("createDate")); // 등록일
		// 페이징
		Pageable pa = PageRequest.of(page, 20, Sort.by(solist));
		// 검색
		Specification<SiteUser> spec = search(kw);

		return userReporitory.findAll(spec, pa);

	}

	// 조회 - 상세

	// 등록

	// 수정

	// 삭제

	// 검색
	private Specification<SiteUser> search(String kw) {
		return new Specification<>() {

			private static final long seriaVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SiteUser> r, CriteriaQuery<?> q, CriteriaBuilder cb) {

				q.distinct(true); // 중복을 제거

				// 사용자 ID, email
				return cb.or(cb.like(r.get("userId"), "%" + kw + "%"), cb.like(r.get("email"), "%" + kw + "%"));
			}
		};
	}
}
