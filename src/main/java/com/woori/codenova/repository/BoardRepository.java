package com.woori.codenova.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woori.codenova.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
