package com.woori.codenova.admin.service;

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
public class AdminUserService {

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

	// 등록
	public SiteUser create(String username, String password, String email) {

		SiteUser item = new SiteUser();
		item.setUsername(username);
		item.setPassword(passwordEncoder.encode(password));
		item.setEmail(email);
		item.setCreateDate(LocalDateTime.now());
//		userReporitory.save(item);

		System.out.println("user insert :: " + item.getId());

//		// 관리자 페이지에서 등록한 사용자는 매니저 권한을 기본으로 가지고 있음.
//		Role role = this.roleReporitory.findByGrade(2).orElse(null);
//		if (role != null) {
//			item.getAuthority().add(role);
//		}

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
				return cb.or(cb.like(r.get("username"), "%" + kw + "%"), cb.like(r.get("email"), "%" + kw + "%"));
			}
		};
	}
}
