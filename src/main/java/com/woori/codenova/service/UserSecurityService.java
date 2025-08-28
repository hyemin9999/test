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
import org.springframework.transaction.annotation.Transactional;

import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//
		Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);

		if (_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}

		SiteUser siteUser = _siteUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();

		if (!siteUser.getAuthority().isEmpty()) {
			if (siteUser.getAuthority().stream().anyMatch(a -> a.getGrade().equals(1))) // 슈퍼관리자
			{
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else {
				authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
			}
//			System.out.println("siteUser.getAuthority() :: ADMIN");

		} else {
//			System.out.println("siteUser.getAuthority() :: USER");
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}

		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
}
