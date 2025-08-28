package com.woori.codenova.admin.service;

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
import com.woori.codenova.entity.Category;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.BoardRepository;
import com.woori.codenova.repository.CategoryRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminBoardService {

	private final BoardRepository boardRepository;
	private final CategoryRepository categoryRepository;

	// 목록 - 페이징 - 검색 종류
	public Page<Board> getList(int page, String kw, String field) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));

		Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));

		// 게시판 - 카테고리
		Specification<Board> spec = search(kw, field);

		return boardRepository.findAll(spec, pageable);
	}

	// 조회 - 상세
	public Board getItem(Integer id) {
		return boardRepository.findById(id).orElse(null);
	}

	// 사용자가 작성한 글 목록 반환
	public List<Board> getListByAuthor(SiteUser id) {
		return boardRepository.findByAuthor(id);
	}

//	public List<Board> getListByVorter(Long id) {
//		return boardRepository.getVoterId(id);
//	}
//
//	public List<Board> getListByFavorites(Long id) {
//		return boardRepository.getFavoritesId(id);
//	}

	// 조회 - 조회수
	public void setViewCount(Board item) {

		int viewCount = item.getViewCount();
		item.setViewCount(viewCount + 1);
		boardRepository.save(item);
	}

	// 등록
	public void create(String subject, String contents, SiteUser uesr) {
		Board item = new Board();
		item.setSubject(subject);
		item.setContents(contents);
		item.setCreateDate(LocalDateTime.now());
		item.setAuthor(uesr);
		item.setViewCount(0);
		// TODO :: 게시판 - 카테고리 // 초기 게시판
		Category citem = categoryRepository.findById(1).orElse(null);
		item.setCategory(citem);

		boardRepository.save(item);
	}

	// 수정
	public void modify(Board item, String subject, String content) {
		item.setSubject(subject);
		item.setContents(content);
		item.setModifyDate(LocalDateTime.now());

		// TODO :: 게시판 - 카테고리 수정도 가능하도록
		Category citem = categoryRepository.findById(1).orElse(null);
		item.setCategory(citem);

		boardRepository.save(item);
	}

	// 삭제 - 실제 item 삭제를 안하고, 제목, 작성자, 내용의 데이터를 날림.
	public void delete(Board item) {
//			item.setSubject("");
//			item.setContents("");
//			item.setDelete(true);
//			item.setDeleteDate(LocalDateTime.now());
//			item.setAuthor(null);
		//
//			// TODO :: 게시글 삭제되어도 게시판과의 연결을 어떻게??
//			// TODO :: 게시글 삭제시 파일 삭제
//			boardRepository.save(item);

		// isDelete 값 false인것만 가지고 처리하는게 힘들어서 그냥 연결된거 일단 다 날림^^

		boardRepository.delete(item);
	}

	public void deleteList(List<Board> list) {
		boardRepository.deleteAll(list);
	}

	// 검색
	private Specification<Board> search(String kw, String field) {
		return new Specification<>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Board> r, CriteriaQuery<?> q, CriteriaBuilder cb) {

				q.distinct(true); // 중복을 제거

				Join<Board, SiteUser> u1 = r.join("author", JoinType.LEFT);
//				Join<Board, Comment> c = r.join("commentList", JoinType.LEFT);
//				Join<Comment, SiteUser> u2 = c.join("author", JoinType.LEFT);

				Predicate byTitle = cb.like(r.get("subject"), "%" + kw + "%"); // 제목
				Predicate byContent = cb.like(r.get("contents"), "%" + kw + "%"); // 내용
				Predicate byAuthor = cb.like(u1.get("username"), "%" + kw + "%"); // 글쓴이(작성자)
//				Predicate byCmt = cb.like(c.get("contents"), "%" + kw + "%"); // 댓글 내용
//				Predicate byCmtUser = cb.like(u2.get("username"), "%" + kw + "%"); // 댓글 작성자

				// TODO :: entity class 수정여부에 따라 바뀌어야 할듯?

				// ✅ 선택한 검색대상에 맞춰 조건 분기
				switch (field) {
				case "title":
					return byTitle;
				case "content":
					return byContent;
				case "author":
					return byAuthor;
				case "all":
				default:
					// 제목+내용(+작성자/댓글/댓글작성자) — 기존처럼 확장 검색
					return cb.or(byTitle, byContent);
				}
			}
		};
	}
}
