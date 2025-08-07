package com.woori.codenova.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	// 페이징 + 검색
	Page<Board> findAll(Specification<Board> specification, Pageable pageable);
}
