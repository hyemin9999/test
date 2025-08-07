package com.woori.codenova.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Role;

public interface RoleReporitory extends JpaRepository<Role, Integer> {

}
