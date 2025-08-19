package com.woori.codenova.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.woori.codenova.entity.Role;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.RoleRepository;
import com.woori.codenova.repository.UserRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	// 초기에 시스템 관리자 정보가 저장되어야 하는게 아닐까??

	private final UserRepository userReporitory;
	private final RoleRepository roleReporitory;
	private final PasswordEncoder passwordEncoder;

	// 목록 - 페이징 - 검색
	public Page<SiteUser> getList(int page, String kw) {

		// 정렬
		List<Sort.Order> solist = new ArrayList<>();
		solist.add(Sort.Order.desc("createDate")); // 등록일
		// 페이징
		Pageable pa = PageRequest.of(page, 20, Sort.by(solist));
		// 검색
		Specification<SiteUser> spec = search(kw);

		return userReporitory.findAll(spec, pa);

	}

	// 조회 - 상세
	public SiteUser getItem(Long id) {
		return userReporitory.findById(id).orElse(null);
	}

	// 조회 - 사용자ID
	public SiteUser getItem(String username) {
		return userReporitory.findByUsername(username).orElse(null);
	}

	// 조회 - 아이디 찾기
	public SiteUser getItemByEmail(String email) {
		return userReporitory.findByEmail(email).orElse(null);
	}

	// 조회 - 아이디 / 비밀번호 찾기
	public SiteUser getItem(String username, String email) {
		return userReporitory.findByUsernameAndEmail(username, email).orElse(null);
	}

	// 등록
	public SiteUser create(String username, String password, String email) {

		SiteUser item = new SiteUser();
		item.setUsername(username);
		item.setPassword(passwordEncoder.encode(password));
		item.setEmail(email);
		item.setCreateDate(LocalDateTime.now());
		userReporitory.save(item);

		// 일반사용자
		Role ritem = roleReporitory.findByGrade(0).orElse(null);

		// Role : grade - 일반사용자(0), 슈퍼관리자(1) - 고정.수정삭제불가
		item.getAuthority().add(ritem);

		return userReporitory.save(item);
	}

	// 수정 - 비밀번호 변경, 회원가입에 사용자ID, 비밀번호, email만 받으면, 수정할수있는게 비밀번호뿐.
	public void modify(SiteUser item, String password) {

		item.setPassword(passwordEncoder.encode(password));
		item.setModifyDate(LocalDateTime.now());

		userReporitory.save(item);
	}

	// 삭제
	public void delete(SiteUser item) {

		// TODO :: 작성한 게시글, 댓글, (관리자)공지사항 처리 여부
		userReporitory.delete(item);
	}

	// 검색
	private Specification<SiteUser> search(String kw) {
		return new Specification<>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SiteUser> r, CriteriaQuery<?> q, CriteriaBuilder cb) {

				q.distinct(true); // 중복을 제거

				// TODO :: 사용자 ID, email
				return cb.or(cb.like(r.get("userId"), "%" + kw + "%"), cb.like(r.get("email"), "%" + kw + "%"));
			}
		};
	}
}
