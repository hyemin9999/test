package com.woori.codenova.admin.controller;

import java.security.Principal;

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

import com.woori.codenova.admin.form.AdminCategoryForm;
import com.woori.codenova.entity.Category;
import com.woori.codenova.service.CategoryService;
import com.woori.codenova.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

	private final CategoryService categoryService;
	private final UserService userService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {

		Page<Category> paging = categoryService.getlist(page, kw);

		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);

		return "admin/category_list";
	}

	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {

		Category item = this.categoryService.getitem(id);

		if (item != null) {
			model.addAttribute("item", item);
		}
		return "admin/category_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String create(Model model, AdminCategoryForm adminCategoryForm) {

		model.addAttribute("mode", "create");
		return "admin/category_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String create(Model model, @Valid AdminCategoryForm adminCategoryForm, BindingResult bindingResult,
			Principal principal) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mode", "create");
			return "admin/category_form";
		}

		this.categoryService.create(adminCategoryForm.getName());
		return "redirect:/admin/category/list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modify(Model model, AdminCategoryForm adminCategoryForm, @PathVariable("id") Integer id,
			Principal principal) {

		model.addAttribute("mode", "modify");
		Category item = this.categoryService.getitem(id);
//		if (!item.getAuthor().getUsername().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//		}
		adminCategoryForm.setName(item.getName());

		return "admin/category_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(Model model, @Valid AdminCategoryForm adminCategoryForm, BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mode", "mofidy");
			return "admin/category_form";
		}

		Category item = this.categoryService.getitem(id);
//		if (!item.getAuthor().getUsername().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//		}

		this.categoryService.modify(item, adminCategoryForm.getName());
		return String.format("redirect:/admin/category/detail/%s", id);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(Principal principal, @PathVariable("id") Integer id) {

		Category item = this.categoryService.getitem(id);
//		if (!item.getAuthor().getUsername().equals(principal.getName())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
//		}
		this.categoryService.delete(item);
		return "redirect:/admin/category/list";
	}

}
