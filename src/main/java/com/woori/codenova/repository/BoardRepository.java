package com.woori.codenova.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	// 페이징 + 검색
//	@EntityGraph(attributePaths = "commentList")
	Page<Board> findAll(Specification<Board> specification, Pageable pageable);

	Page<Board> findAll(Pageable pageable);

//	@Query("SELECT b FROM Board b JOIN FETCH b.commentList WHERE b.id = :id")
//	@Query("SELECT b FROM Board b JOIN b.commentList c WHERE b.id = :id")

	// @Query("SELECT distinct b FROM Board b " + "LEFT OUTER JOIN Comment c ON
	// c.board.id = :id ")
	// + "WHERE b.commentList = null or ( b.commentList != null and c.isDelete !=
	// true )")

//	@Query("SELECT b FROM Board b LEFT JOIN Comment c ON b = c.board where b.id = :id "// )
//			+ " or (c IS NOT NULL and c.isDelete = falase)")
	// @Query("SELECT b FROM Board b JOIN FETCH b.commentList WHERE b.id = :id")
//	Optional<Board> findByIdAndNotDelete(@Param("id") Integer id);
}
