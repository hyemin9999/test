package com.woori.codenova.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.SiteUser;

public interface UserReporitory extends JpaRepository<SiteUser, Long> {
	// 페이징 + 검색
	Page<SiteUser> findAll(Specification<SiteUser> specification, Pageable pageable);
}
