package com.woori.codenova.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.SiteUser;

public interface UserReporitory extends JpaRepository<SiteUser, Long> {
	// 페이징 + 검색
	Page<SiteUser> findAll(Specification<SiteUser> specification, Pageable pageable);

	// 아이디찾기
	SiteUser findByEmail(String email);

	// 비밀번호 찾기 - 변경을 위한 선처리
	SiteUser findByUsernameAndEmail(String username, String email);
}
