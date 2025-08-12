package com.woori.codenova.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.woori.codenova.UserRole;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.RoleRepository;
import com.woori.codenova.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//
		Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);

		if (_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}

		SiteUser siteUser = _siteUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();

		// 관리자(UserRole.ADMIN)나 사용자(UserRole.USER)권한을 준다. ==> 인가
		if ("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}

//		Optional<Role> _role = this.roleRepository.findByName("관리자");
//		if (_role.isEmpty()) {
//			throw new UsernameNotFoundException("역할을 찾을수 없습니다.");
//		}
//
//		Role role = _role.get();

		// 로그인한 사용자의 역할을 db에서 검색해서 처리해야함

//		siteUser.getAuthority().;

//		authorities = getAuthorities(siteUser.getAuthority());

		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}

//	private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		for (Role role : roles) {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
//		return authorities;
//	}
}
