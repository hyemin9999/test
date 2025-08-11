package com.woori.codenova.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Board;
import com.woori.codenova.entity.Comment;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.BoardRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	// 초기에 시스템 관리자 정보가 저장되어야 하는게 아닐까??

	private final BoardRepository boardRepository;

	public List<Board> getlist() {
		return boardRepository.findAll();
	}

	public Page<Board> getlist(int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));

		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));

		return boardRepository.findAll(pageable);
	}

	// 목록 - 페이징 - 검색
	public Page<Board> getlist(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));

		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));

		// 게시판 - 카테고리
		Specification<Board> spec = search(kw);

		return boardRepository.findAll(spec, pageable);
	}

	// 조회 - 상세
	public Board getitem(Integer id) {

		return boardRepository.findById(id).orElse(null);
	}

	// 등록
	public void create(String subject, String contents, SiteUser uesr) {
		Board item = new Board();
		item.setSubject(subject);
		item.setContents(contents);
		item.setCreateDate(LocalDateTime.now());
		item.setAuthor(uesr);

		// 게시판 - 카테고리
		boardRepository.save(item);
	}

	// 수정
	public void modify(Board item, String subject, String content) {
		item.setSubject(subject);
		item.setContents(content);
		item.setModifyDate(LocalDateTime.now());
		boardRepository.save(item);
	}

	// 삭제 - 실제 item 삭제를 안하고, 제목, 작성자, 내용의 데이터를 날림.
	public void delete(Board item) {
		item.setSubject("");
		item.setContents("");
		item.setDelete(true);
		item.setDeleteDate(LocalDateTime.now());
		item.setAuthor(null);
		boardRepository.save(item);
		// boardRepository.delete(item);
	}

	// 추천
	public void vote(Board item, SiteUser siteUser) {
		item.getVoter().add(siteUser);
		boardRepository.save(item);
	}

	// 즐겨찾기
	public void favorites(Board item, SiteUser siteUser) {
		item.getFavorites().add(siteUser);
		boardRepository.save(item);
	}

	// 검색
	private Specification<Board> search(String kw) {
		return new Specification<>() {

			private static final long seriaVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Board> r, CriteriaQuery<?> q, CriteriaBuilder cb) {

				q.distinct(true); // 중복을 제거

				Join<Board, SiteUser> u = r.join("author", JoinType.LEFT);// 게시글과 작성자
				Join<Board, Comment> c = r.join("comment", JoinType.LEFT);// 게시글과 댓글
				Join<Comment, SiteUser> u1 = r.join("author", JoinType.LEFT);// 댓글과 작성자

				// 제목, 내용, 작성자ID, 댓글(내용, 작성자ID)
				return cb.or(cb.like(r.get("subject"), "%" + kw + "%"), // 게시글 제목
						cb.like(r.get("content"), "%" + kw + "%"), // 댓글 내용
						cb.like(u.get("userId"), "%" + kw + "%"), // 게시글 작성자
						cb.like(c.get("content"), "%" + kw + "%"), // 댓글 내용
						cb.like(u1.get("userId"), "%" + kw + "%")); // 댓글 작성자
			}
		};
	}
}
