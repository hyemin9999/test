package com.woori.codenova;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String root() {
		return "index";
	}

	@GetMapping(value = { "/notice", "/notice/" })
	public String notice() {
		return "redirect:/notice/list";
	}

	@GetMapping(value = { "/board", "/board/" })
	public String board() {
		return "redirect:/board/list";
	}

	@GetMapping(value = { "/admin", "/admin/", "/admin/user", "/admin/user/" })
	public String admin() {
		return "redirect:/admin/user/list";
	}

	@GetMapping(value = { "/admin/notice", "/admin/notice/" })
	public String adminNotice() {
		return "redirect:/admin/notice/list";
	}

	@GetMapping(value = { "/admin/category", "/admin/category/" })
	public String adminCategory() {
		return "redirect:/admin/category/list";
	}

	@GetMapping(value = { "/admin/board", "/admin/board/" })
	public String adminBoard() {
		return "redirect:/admin/board/list";
	}
}
