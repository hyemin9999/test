package com.woori.codenova.controller;

import java.security.Principal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.form.UserForm;
import com.woori.codenova.form.UserModifyForm;
import com.woori.codenova.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/signup")
	public String signup(UserForm uitemForm) {

		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid UserForm uitemForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!uitemForm.getPassword1().equals(uitemForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");

			return "signup_form";
		}

		try {
			this.userService.create(uitemForm.getUsername(), uitemForm.getPassword1(), uitemForm.getEmail());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");

			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());

			return "signup_form";
		}

		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {

		return "login_form";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/info")
	public String detail(Model model, UserModifyForm userModifyForm, Principal principal) {
		model.addAttribute("mode", "info");

		SiteUser item = this.userService.getItem(principal.getName());
		if (item == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}

		userModifyForm.setId(item.getId());
		userModifyForm.setUsername(item.getUsername());
		userModifyForm.setEmail(item.getEmail());

		return "user_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/info")
	public String detail(Model model, @Valid UserModifyForm userModifyForm, BindingResult bindingResult,
			Principal principal) {

		SiteUser item = this.userService.getItem(principal.getName());

		model.addAttribute("mode", "modify");

		userModifyForm.setId(item.getId());
		userModifyForm.setUsername(item.getUsername());
		userModifyForm.setEmail(item.getEmail());

		if (bindingResult.hasErrors()) {

			return "user_detail";
		}

		if (!item.getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}

		if (!userModifyForm.getPassword1().equals(userModifyForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "변경 비밀번호가 일치하지 않습니다.");
			return "user_detail";
		}

		if (!passwordEncoder.matches(userModifyForm.getPassword(), item.getPassword())) {
			bindingResult.rejectValue("password", "passwordInCorrect", "현재 비밀번호가 일치하지 않습니다.");
			return "user_detail";
		}

		this.userService.modify(item, userModifyForm.getPassword1());
		model.addAttribute("mode", "info");
		return String.format("redirect:/user/info");
	}

}
