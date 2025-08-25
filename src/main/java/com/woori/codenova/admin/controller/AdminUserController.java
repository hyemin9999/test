package com.woori.codenova.admin.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
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

import com.woori.codenova.admin.form.AdminUserForm;
import com.woori.codenova.admin.form.AdminUserModifyForm;
import com.woori.codenova.admin.service.AdminRoleService;
import com.woori.codenova.admin.service.AdminUserService;
import com.woori.codenova.entity.Role;
import com.woori.codenova.entity.SiteUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

	private final AdminUserService adminUserService;
	private final AdminRoleService adminRoleService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw, AdminUserForm adminUserForm,
			BindingResult bindingResult) {

		list(model, page, kw, adminUserForm, "list");

		return "admin/user_list";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/list")
	public String create(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw, @Valid AdminUserForm adminUserForm,
			BindingResult bindingResult) {

		list(model, page, kw, adminUserForm, "create");

		if (bindingResult.hasErrors()) {

			return "admin/user_list";
		}

		try {
			this.adminUserService.create(adminUserForm.getUsername(), adminUserForm.getPassword1(),
					adminUserForm.getEmail(), adminUserForm.getSelectedList());

			return "redirect:/admin/user/list";
		} catch (DataIntegrityViolationException e) {

			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");

			return "admin/user_list";
		} catch (Exception e) {

			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());

			return "admin/user_list";
		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list/{id}")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw, AdminUserModifyForm adminUserModifyForm,
			BindingResult bindingResult, Principal principal, @PathVariable("id") Long id) {

		listById(model, page, kw, principal, adminUserModifyForm, id, "list");

		return "admin/user_list";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/list/{id}")
	public String modify(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw, @Valid AdminUserModifyForm adminUserModifyForm,
			BindingResult bindingResult, Principal principal, @PathVariable("id") Long id) {

		SiteUser item = this.adminUserService.getItem(id);
		if (item == null) {
			model.addAttribute("message", "존재하지 않는 회원 입니다.");
			bindingResult.reject("존재하지 않는 회원 입니다.");
		}

		listById(model, page, kw, principal, adminUserModifyForm, id, "modify");

		if (bindingResult.hasErrors()) {

			return "admin/user_list";
		}

		this.adminUserService.modify(item, adminUserModifyForm.getPassword1(), adminUserModifyForm.getSelectedList());
		return String.format("redirect:/admin/user/list");
	}

	public void list(Model model, Integer page, String kw, AdminUserForm adminUserForm, String mode) {

		Page<SiteUser> paging = adminUserService.getList(page, kw);

		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("mode", "create");

		if (mode == "list") {
			List<Role> rlist = adminRoleService.getlist();
			adminUserForm.setOptionList(rlist);
		}
	}

	public void listById(Model model, Integer page, String kw, Principal principal,
			AdminUserModifyForm adminUserModifyForm, Long id, String mode) {

		SiteUser item = this.adminUserService.getItem(id);
		if (item == null) {
			model.addAttribute("message", "존재하지 않는 회원 입니다.");
//			model.addAttribute("item", item);
		}

		Page<SiteUser> paging = adminUserService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("mode", "modify");

		adminUserModifyForm.setUsername(item.getUsername());
		adminUserModifyForm.setEmail(item.getEmail());

//		SiteUser user = this.adminUserService.getItem(principal.getName());
//		if (user != null && !user.getAuthority().isEmpty()
//				&& user.getAuthority().stream().anyMatch(a -> a.getGrade().equals(1))) {
//			model.addAttribute("mode", "modify");
//		}

		if (mode == "list") {
			List<Role> optionList = adminRoleService.getlist();
			adminUserModifyForm.setOptionList(optionList);

			List<Role> selectedlist = new ArrayList<>(item.getAuthority()); // 사용자에게 할당된 역할목록?
			adminUserModifyForm.setSelectedList(selectedlist);
		}
	}

//	@PreAuthorize("isAuthenticated()")
//	@GetMapping("/create")
//	public String create(AdminUserForm adminUserForm) {
//
//		return "admin/user_form";
//	}

//	@PreAuthorize("isAuthenticated()")
//	@PostMapping("/create")
//	public String create(@Valid AdminUserForm adminUserForm, BindingResult bindingResult) {
//
//		if (bindingResult.hasErrors()) {
//			return "admin/user_form";
//		}
//
//		try {
//			this.adminUserService.create(adminUserForm.getUsername(), adminUserForm.getPassword1(),
//					adminUserForm.getEmail());
//		} catch (DataIntegrityViolationException e) {
//			e.printStackTrace();
//			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
//
//			return "admin/user_form";
//		} catch (Exception e) {
//			e.printStackTrace();
//			bindingResult.reject("signupFailed", e.getMessage());
//
//			return "admin/user_form";
//		}
//
//		return "redirect:/admin/user/list";
//	}

//	@PreAuthorize("isAuthenticated()")
//	@GetMapping(value = "/detail/{id}")
//	public String detail(Model model, AdminUserModifyForm adminUserModifyForm, Principal principal,
//			@PathVariable("id") Long id) {
//
//		SiteUser item = this.adminUserService.getItem(id);
//		if (item == null) {
//			model.addAttribute("message", "존재하지 않는 회원 입니다.");
//			return "admin/user_detail";
//		}
//
//		model.addAttribute("item", item);
//		model.addAttribute("mode", "detail");
//
//		adminUserModifyForm.setId(item.getId());
//		adminUserModifyForm.setUsername(item.getUsername());
//		adminUserModifyForm.setEmail(item.getEmail());
//
//		return "admin/user_detail";
//	}
//
//	
//	
//	@PreAuthorize("isAuthenticated()")
//	@PostMapping(value = "/detail/{id}")
//	public String detail(Model model, @Valid AdminUserModifyForm adminUserModifyForm, BindingResult bindingResult,
//			Principal principal, @PathVariable("id") Long id) {
//
//		SiteUser item = this.adminUserService.getItem(id);
//		if (item == null) {
//			model.addAttribute("message", "존재하지 않는 회원 입니다.");
//			bindingResult.reject("존재하지 않는 회원 입니다.");
//		}
//
//		model.addAttribute("mode", "modify");
//		model.addAttribute("item", item);
//
//		adminUserModifyForm.setId(item.getId());
//		adminUserModifyForm.setUsername(item.getUsername());
//		adminUserModifyForm.setEmail(item.getEmail());
//
//		if (bindingResult.hasErrors()) {
//			return "admin/user_detail";
//		}
//
//		this.adminUserService.modify(item, adminUserModifyForm.getPassword1());
//		return String.format("redirect:/admin/user/list");
//	}

}
