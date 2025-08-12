package com.woori.codenova.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.woori.codenova.entity.Board;
import com.woori.codenova.entity.Comment;
import com.woori.codenova.entity.SiteUser;
import com.woori.codenova.form.CommentForm;
import com.woori.codenova.service.BoardService;
import com.woori.codenova.service.CommentService;
import com.woori.codenova.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

	private final BoardService boardService;
	private final CommentService commentService;
	private final UserService userService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{bid}")
	public String creater(Model model, @PathVariable("bid") Integer bid, @Valid CommentForm commentForm,
			BindingResult bindingResult, Principal principal) {

		Board bitem = this.boardService.getItem(bid);
		SiteUser author = this.userService.getItem(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("bitem", bitem);

			return "board_detail";
		}
		Comment item = this.commentService.create(bitem, commentForm.getContent(), author);
		return String.format("redirect:/board/detail/%s#comment_%s", bid, item.getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modify(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {

		Comment item = this.commentService.getItem(id);
		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		commentForm.setContent(item.getContents());
		return "comment_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(@Valid CommentForm commentForm, BindingResult bindingResult, @PathVariable("id") Integer id,
			Principal principal) {

		if (bindingResult.hasErrors()) {
			return "comment_form";
		}
		Comment item = this.commentService.getItem(id);
		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.commentService.modify(item, commentForm.getContent());
		return String.format("redirect:/board/detail/%s#comment_%s", item.getBoard().getId(), item.getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String delete(Principal principal, @PathVariable("id") Integer id) {

		Comment item = this.commentService.getItem(id);
		if (!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
		}
		this.commentService.delete(item);
		return String.format("redirect:/board/detail/%s", item.getBoard().getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String vote(Principal principal, @PathVariable("id") Integer id) {

		Comment item = this.commentService.getItem(id);
		SiteUser user = this.userService.getItem(principal.getName());
		this.commentService.vote(item, user);
		return String.format("redirect:/board/detail/%s#comment_%s", item.getBoard().getId(), item.getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/favorites/{id}")
	public String favorites(Principal principal, @PathVariable("id") Integer id) {

		Comment item = this.commentService.getItem(id);
		SiteUser user = this.userService.getItem(principal.getName());
		this.commentService.favorites(item, user);
		return String.format("redirect:/board/detail/%s#comment_%s", item.getBoard().getId(), item.getId());
	}
}