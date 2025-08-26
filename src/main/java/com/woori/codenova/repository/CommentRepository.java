package com.woori.codenova.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Comment;
import com.woori.codenova.entity.SiteUser;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

//	@Query("SELECT c FROM Comment c LEFT JOIN SiteUser u ON c.author = u WHERE u.id = :id")
//	List<Comment> getByAuthorId(@Param("id") Long id);

	// 작성자
	List<Comment> findByAuthor(SiteUser author);

}
