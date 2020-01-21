package com.devyu.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.User;
import com.devyu.blog.inputForm.BlogForm;
import com.devyu.blog.service.BlogService;
import com.devyu.blog.service.TagService;
import com.devyu.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BlogController {
	
	private final UserService userService;
	private final BlogService blogService;
	private final TagService tagService;
	

	@GetMapping("/blog")
	public String blogList(Model model) {
		List<Blog> blogList = blogService.findAll();
		model.addAttribute("blogList", blogList);
		return "blog/list";
	}
	
	@GetMapping("/blog/{id}")
	public String detail(@PathVariable Long id, Model model) {
		Blog blog = blogService.findOne(id);
		User user = userService.findOne(blog.getUser().getId());
		model.addAttribute("blog", blog);
		model.addAttribute("user", user);
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
