package com.woori.codenova.admin.controller;

import java.security.Principal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.woori.codenova.admin.form.AdminUserForm;
import com.woori.codenova.admin.form.AdminUserModifyForm;
import com.woori.codenova.admin.service.AdminUserService;
import com.woori.codenova.entity.SiteUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

	private final AdminUserService adminUserService;
	private final PasswordEncoder passwordEncoder;

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {

		Page<SiteUser> paging = adminUserService.getList(page, kw);

		model.addAttribute("paging", paging);
		// 입력한 검색어를 화면에 그대로 유지
		model.addAttribute("kw", kw);

		return "admin/user_list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String create(AdminUserForm adminUserForm) {

		return "admin/user_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String create(@Valid AdminUserForm adminUserForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "admin/user_form";
		}

		try {
			this.adminUserService.create(adminUserForm.getUsername(), adminUserForm.getPassword1(),
					adminUserForm.getEmail());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");

			return "admin/user_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());

			return "admin/user_form";
		}

		return "redirect:/admin";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, AdminUserModifyForm adminUserModifyForm, Principal principal,
			@PathVariable("id") Long id) {

		SiteUser item = this.adminUserService.getItem(id);
		if (item == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지않는 회원입니다.");
		}

		model.addAttribute("mode", "detail");
		model.addAttribute("item", item);

		adminUserModifyForm.setId(item.getId());
		adminUserModifyForm.setUsername(item.getUsername());
		adminUserModifyForm.setEmail(item.getEmail());

		return "admin/user_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/detail/{id}")
	public String detail(Model model, @Valid AdminUserModifyForm adminUserModifyForm, BindingResult bindingResult,
			Principal principal, @PathVariable("id") Long id) {

		SiteUser item = this.adminUserService.getItem(id);
		if (item == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지않는 회원입니다.");
		}

		model.addAttribute("mode", "modify");
		model.addAttribute("item", item);

		adminUserModifyForm.setId(item.getId());
		adminUserModifyForm.setUsername(item.getUsername());
		adminUserModifyForm.setEmail(item.getEmail());

		if (bindingResult.hasErrors()) {
			return "admin/user_detail";
		}

		this.adminUserService.modify(item, adminUserModifyForm.getPassword1());
		model.addAttribute("mode", "info");
		return String.format("redirect:/admin/user/list");
	}

}
