package com.woori.codenova.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.woori.codenova.service.BoardService;
import com.woori.codenova.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	// DI (객체 주입) --> UserService 추가
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
		model.addAttribute("list", this.boardService.getlist());
		return "board_list";

	}

//	@GetMapping("/list")
//	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
//		Page<Board> paging = this.boardService.getlist(page);
//		model.addAttribute("paging", paging);
//		return "board_list";
//
//	}

//	@GetMapping("/list")
//	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "kw", defaultValue = "") String kw) {
//		Page<Board> paging = boardService.getlist(page, kw);
//
//		System.out.println(paging.isEmpty() + "::paging.isEmpty()::");
//
//		model.addAttribute("paging", paging);
//		// 입력한 검색어를 화면에 그대로 유지
//		model.addAttribute("kw", kw);
//		return "board_list";
//	}

}
