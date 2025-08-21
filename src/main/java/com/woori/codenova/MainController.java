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

	@GetMapping(value = { "/admin", "/admin/", "/admin/u", "/admin/user", "/admin/user/", })
	public String admin() {
		return "redirect:/admin/user/list";
	}

	@GetMapping(value = { "/admin/user/d", "/admin/user/detail", "/admin/user/detail/" })
	public String adminUserDeatil() {
		return "redirect:/admin/user/detail/0";
	}

	@GetMapping(value = { "/admin/r", "/admin/role", "/admin/role/" })
	public String adminRole() {
		return "redirect:/admin/roles/list";
	}

	@GetMapping(value = { "/admin/n", "/admin/notice", "/admin/notice/" })
	public String adminNotice() {
		return "redirect:/admin/notice/list";
	}

	@GetMapping(value = { "/admin/c", "/admin/category", "/admin/category/" })
	public String adminCategory() {
		return "redirect:/admin/category/list";
	}

	@GetMapping(value = { "/admin/b", "/admin/board", "/admin/board/" })
	public String adminBoard() {
		return "redirect:/admin/board/list";
	}
}
