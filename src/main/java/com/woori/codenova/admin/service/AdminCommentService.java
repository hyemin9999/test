package com.woori.codenova.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Comment;
import com.woori.codenova.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminCommentService {

	private final CommentRepository commentRepository;

	// 사용자가 작성한 글 목록 반환
	public List<Comment> getListByAuthorId(Long id) {
		return commentRepository.getByAuthorId(id);
	}
}
