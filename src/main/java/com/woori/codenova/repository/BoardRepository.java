package com.woori.codenova.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.woori.codenova.entity.Board;
import com.woori.codenova.entity.SiteUser;

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

	@Query(value = "SELECT b FROM Board b WHERE b.author.id = :id", nativeQuery = true)
	List<Board> getByAuthorId(@Param("id") Long id); // 작성한 게시글

	List<Board> findByAuthor(SiteUser author);

//	@Query("SELECT b FROM Board b LEFT JOIN b.voter u ON b.voter = u WHERE u.voterId = :id")
//	List<Board> getVoterId(@Param("id") Long id);// 추천한 게시글
//
//	@Query("SELECT b FROM Board b LEFT JOIN b.favorites u ON b.favorites = u WHERE u.favoritesId = :id")
//	List<Board> getFavoritesId(@Param("id") Long id);// 즐겨찾기 게시글
}
