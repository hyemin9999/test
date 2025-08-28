package com.woori.codenova.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Board;
import com.woori.codenova.entity.Comment;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	// 초기에 시스템 관리자 정보가 저장되어야 하는게 아닐까??

	private final CommentRepository commentRepository;
//	private final BoardRepository boardRepository;

	// 목록
	public List<Comment> getList() {
		return commentRepository.findAll();
	}

	// 조회 - 상세
	public Comment getItem(Integer id) {

		return commentRepository.findById(id).orElse(null);
	}

	// 등록
	public Comment create(Board bitem, String contents, SiteUser uesr) {
		Comment item = new Comment();

		item.setContents(contents);
		item.setCreateDate(LocalDateTime.now());
		item.setAuthor(uesr);
		item.setBoard(bitem);

		return commentRepository.save(item);
	}

	// 수정
	public void modify(Comment item, String content) {

		item.setContents(content);
		item.setModifyDate(LocalDateTime.now());
		commentRepository.save(item);
	}

	// 삭제 - 실제 item 삭제를 안하고, 작성자, 내용의 데이터를 날림.
	public void delete(Comment item) {

//		item.setContents("");
//		item.setDelete(true);
//		item.setDeleteDate(LocalDateTime.now());
//		item.setAuthor(null);
//		commentReporitory.save(item);

		// TODO :: repository로 isdelete 체크하는게 좀 까다로움

		commentRepository.delete(item);
	}

	// 추천
	public void vote(Comment item, SiteUser siteUser) {
		// TODO :: 추천, 취소 처리

		item.getVoter().add(siteUser);
		commentRepository.save(item);
	}

	// 즐겨찾기
	public void favorites(Comment item, SiteUser siteUser) {
		// TODO :: 즐겨찾기, 취소 처리
		item.getFavorite().add(siteUser);
		commentRepository.save(item);
	}

	public Page<Comment> getPageByBoard(Board board, int page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("createDate")));
		return commentRepository.findByBoard(board, pageable);
	}

}
