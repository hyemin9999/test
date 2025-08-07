package com.woori.codenova.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Role;

public interface RoleReporitory extends JpaRepository<Role, Integer> {
	// 페이징 + 검색
	Page<Role> findAll(Specification<Role> specification, Pageable pageable);

}
