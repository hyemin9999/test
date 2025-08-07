package com.woori.codenova.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.User;

public interface UserReporitory extends JpaRepository<User, Long> {

}
