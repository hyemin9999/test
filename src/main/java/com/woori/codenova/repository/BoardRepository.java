package com.woori.codenova.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Board;
import com.woori.codenova.entity.SiteUser;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	// 페이징 + 검색
	Page<Board> findAll(Specification<Board> specification, Pageable pageable);

	Page<Board> findAll(Pageable pageable);

//	@Query(value = "SELECT b FROM Board b WHERE b.author.id = :id", nativeQuery = true)
//	List<Board> getByAuthorId(@Param("id") Long id); // 작성한 게시글

	// 작성자
	List<Board> findByAuthor(SiteUser author);

}
