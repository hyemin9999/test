package com.woori.codenova.admin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.woori.codenova.admin.form.AdminRoleForm;
import com.woori.codenova.admin.service.AdminCategoryService;
import com.woori.codenova.admin.service.AdminRoleService;
import com.woori.codenova.entity.Category;
import com.woori.codenova.entity.Role;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/role")
@RequiredArgsConstructor
public class AdminRoleController {

	private final AdminRoleService adminRoleService;
	private final AdminCategoryService adminCategoryService;
	private final UserService userService;

	@GetMapping("/list")
	@PreAuthorize("isAuthenticated()")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw, AdminRoleForm adminRoleForm,
			BindingResult bindingResult) {

		Page<Role> paging = adminRoleService.getList(page, kw);

		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("mode", "info");

		List<Category> clist = this.adminCategoryService.getlist();
		model.addAttribute("clist", clist);

		return "admin/role_list";
	}

	@GetMapping("/list/{id}")
	@PreAuthorize("isAuthenticated()")
	public String listById(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw, AdminRoleForm adminRoleForm,
			BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {

		Page<Role> paging = adminRoleService.getList(page, kw);

		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);

		Role item = this.adminRoleService.getItem(id);

		if (item != null) {
			adminRoleForm.setName(item.getName());

			List<Category> clist = this.adminCategoryService.getlist();
			model.addAttribute("clist", clist);
		}

		SiteUser user = this.userService.getItem(principal.getName());
		if (user != null && !user.getAuthority().isEmpty()
				&& user.getAuthority().stream().anyMatch(a -> a.getGrade().equals(1))) {
			model.addAttribute("mode", "modify");
		}

		return "admin/role_list";
	}

	@PostMapping("/list")
	@PreAuthorize("isAuthenticated()")
	public String create(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw, @Valid AdminRoleForm adminRoleForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "admin/role_list";
		}

		this.adminRoleService.create(adminRoleForm.getName(), 2);
		return "redirect:/admin/role/list";
	}

	@PostMapping("/list/{id}")
	@PreAuthorize("isAuthenticated()")
	public String modify(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw, @Valid AdminRoleForm adminRoleForm,
			BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {

		if (bindingResult.hasErrors()) {
			return "admin/role_list";
		}

		Role item = this.adminRoleService.getItem(id);
//		if (!item.getAuthor().getUsername().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//		}

		this.adminRoleService.modify(item, adminRoleForm.getName(), item.getGrade());
		return "redirect:/admin/role/list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(Principal principal, @PathVariable("id") Integer id) {

		Role item = this.adminRoleService.getItem(id);
//		if (!item.getAuthor().getUsername().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//		}
		this.adminRoleService.delete(item);
		return "redirect:/admin/role/list";
	}
}
