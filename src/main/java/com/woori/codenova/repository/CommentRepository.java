package com.woori.codenova.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.woori.codenova.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("SELECT c FROM Comment c LEFT JOIN SiteUser u ON c.author = u WHERE u.id = :id")
	List<Comment> getByAuthorId(@Param("id") Long id);

}
