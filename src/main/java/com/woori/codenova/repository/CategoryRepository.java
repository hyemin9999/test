package com.woori.codenova.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	// 페이징 + 검색
	Page<Category> findAll(Specification<Category> specification, Pageable pageable);
}
