package com.devyu.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.inputForm.BlogForm;
import com.devyu.blog.service.BlogService;
import com.devyu.blog.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BlogController {
	
	private final BlogService blogService;
	private final TagService tagService;
	

	@GetMapping("/blog")
	public String blogList(Model model) {
		List<Blog> blogList = blogService.findAll();
		model.addAttribute("blogList", blogList);
		return "blog/list";
	}
	
	@GetMapping("/blog/detail")
	public String detail() {
		return "blog/detail";
	}
	
	@GetMapping("/blog/create")
	public String createForm() {
		return "blog/createForm";
	}
	
	@PostMapping("blog/create")
	public String create(HttpServletRequest req, BlogForm blogForm, HttpSession session) {
		
		// 블로그
		Blog blog = blogService.create(blogForm, session);
		
		// 태그
		String[] tags = req.getParameterValues("tagName");
		tagService.create(tags, blog);

		return "redirect:/blog";
	}
}
