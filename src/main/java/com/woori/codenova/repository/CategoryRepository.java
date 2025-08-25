package com.woori.codenova.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	// 페이징 + 검색
	Page<Category> findAll(Specification<Category> specification, Pageable pageable);

	List<Category> findAll(Specification<Category> specification);

//	// 역할 관리에서 사용할 게시판 목록 반환 처리 - 이름순으로 정렬
//	@Query("SELECT new com.woori.codenova.dto.AdminCategoryDto(c.id, c.name, false) FROM Category c "
//			+ "ORDER BY name ASC ")
////			+ "LEFT JOIN c.authority ca ")
//	List<AdminCategoryDto> findAllByAuthority();

}
