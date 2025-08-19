package com.woori.codenova.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Role;
import com.woori.codenova.repository.RoleRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoleService {

	// 초기에 시스템 관리자와 일반 사용자의 역할이 저장되어야 하는게 아닐까??

	private final RoleRepository roleReporitory;

	// 목록 - 페이징 - 검색
	public Page<Role> getList(int page, String kw) {

		// 정렬
		List<Sort.Order> solist = new ArrayList<>();
		solist.add(Sort.Order.asc("grade")); // 등급
		solist.add(Sort.Order.asc("createDate")); // 등록일
		// 페이징
		Pageable pa = PageRequest.of(page, 20, Sort.by(solist));
		// 검색
		Specification<Role> spec = search(kw);

		return roleReporitory.findAll(spec, pa);
	}

	// 조회 - 상세
	public Role getItem(Integer id) {
		return roleReporitory.findById(id).orElse(null);
	}

	// 등록
	public Role create(String name, Integer grade) {

		Role item = new Role();
		item.setName(name);
		item.setGrade(grade);
		item.setCreateDate(LocalDateTime.now());

		return roleReporitory.save(item);
	}

	// 수정
	public Role modify(Role item, String name, Integer grade) {
		item.setName(name);
		item.setGrade(grade);
		item.setModifyDate(LocalDateTime.now());

		return roleReporitory.save(item);
	}

	// 삭제
	public void delete(Role item) {

		// TODO :: 연동된 권한이나 사용자가 있을때 삭제처리 여부
		roleReporitory.delete(item);
	}

	// 검색
	private Specification<Role> search(String kw) {
		return new Specification<>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Role> r, CriteriaQuery<?> q, CriteriaBuilder cb) {

				q.distinct(true); // 중복을 제거

				// TODO :: 명칭, 등급
				return cb.or(cb.like(r.get("name"), "%" + kw + "%"), cb.like(r.get("grade"), "%" + kw + "%"));
			}
		};
	}

}
