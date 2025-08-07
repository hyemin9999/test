package com.woori.codenova.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Comment;

public interface CommentReporitory extends JpaRepository<Comment, Integer> {

}
