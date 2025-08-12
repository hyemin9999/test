package com.woori.codenova.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
	// 페이징 + 검색
	Page<SiteUser> findAll(Specification<SiteUser> specification, Pageable pageable);

	Optional<SiteUser> findByUsername(String username);

	// 아이디찾기
	Optional<SiteUser> findByEmail(String email);

	// 비밀번호 찾기 - 변경을 위한 선처리
	Optional<SiteUser> findByUsernameAndEmail(String username, String email);
}
