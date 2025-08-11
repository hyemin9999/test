package com.woori.codenova.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Comment;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.BoardRepository;
import com.woori.codenova.repository.CommentReporitory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	// 초기에 시스템 관리자 정보가 저장되어야 하는게 아닐까??

	private final CommentReporitory commentReporitory;

	private final BoardRepository boardRepository;

	// 목록
	public List<Comment> getlist() {
		return commentReporitory.findAll();
	}

	// 조회 - 상세
	public Comment getitem(Integer id) {

		return commentReporitory.findById(id).orElse(null);
	}

	// 등록
	public void create(String contents, SiteUser uesr) {
		Comment item = new Comment();

		item.setContents(contents);
		item.setCreateDate(LocalDateTime.now());
		item.setAuthor(uesr);

		// 게시판 - 카테고리
		commentReporitory.save(item);
	}

	// 수정
	public void modify(Comment item, String content) {

		item.setContents(content);
		item.setModifyDate(LocalDateTime.now());
		commentReporitory.save(item);
	}

	// 삭제 - 실제 item 삭제를 안하고, 작성자, 내용의 데이터를 날림.
	public void delete(Comment item) {

		item.setContents("");
		item.setDelete(true);
		item.setDeleteDate(LocalDateTime.now());
		item.setAuthor(null);
		commentReporitory.save(item);
		// commentReporitory.delete(item);
	}

	// 추천
	public void vote(Comment item, SiteUser siteUser) {
		item.getVoter().add(siteUser);
		commentReporitory.save(item);
	}

	// 즐겨찾기
	public void favorites(Comment item, SiteUser siteUser) {
		item.getFavorites().add(siteUser);
		commentReporitory.save(item);
	}

}
