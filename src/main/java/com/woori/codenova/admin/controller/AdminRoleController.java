package com.woori.codenova.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.woori.codenova.admin.service.AdminRoleService;
import com.woori.codenova.entity.Role;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/role")
@RequiredArgsConstructor
public class AdminRoleController {

	private final AdminRoleService adminRoleService;

	@GetMapping("/list")
	@PreAuthorize("isAuthenticated()")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {

//			, AdminRoleForm adminRoleForm,
//			BindingResult bindingResult) {

		Page<Role> paging = adminRoleService.getList(page, kw);

		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);

		model.addAttribute("mode", "info");

		return "admin/role_list";
	}

//	@GetMapping("/list/{id}")
//	@PreAuthorize("isAuthenticated()")
//	public String listById(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "kw", defaultValue = "") String kw, AdminRoleForm adminRoleForm,
//			BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
//
//		Page<Role> paging = adminRoleService.getList(page, kw);
//
//		model.addAttribute("paging", paging);
//		model.addAttribute("kw", kw);
//
//		Role item = this.adminRoleService.getItem(id);
//
//		if (item != null) {
//			adminRoleForm.setName(item.getName());
//		}
//
////		SiteUser user = this.userService.getItem(principal.getName());
////		if (user != null && !user.getAuthority().isEmpty()
////				&& user.getAuthority().stream().anyMatch(a -> a.getGrade().equals(1))) {
////			model.addAttribute("mode", "modify");
////		}
//
//		return "admin/role_list";
//	}
}
