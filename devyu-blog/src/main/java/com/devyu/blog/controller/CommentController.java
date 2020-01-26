package com.devyu.blog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devyu.blog.domain.Comment;
import com.devyu.blog.inputForm.CommentForm;
import com.devyu.blog.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	
	@PostMapping("/blog/{id}/comment")
	public Comment create(@PathVariable Long id, CommentForm commentForm) {
		return commentService.create(id, commentForm);
	}
	
	@GetMapping("/blog/{id}/comment")
	public List<Comment> list(@PathVariable Long id) {
		return commentService.findAllByBlogId(id);
	}
}
