package com.woori.codenova.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

}
