package com.devyu.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {

	@GetMapping("/blog")
	public String List() {
		return "blog/list";
	}
	
	@GetMapping("/blog/detail")
	public String detail() {
		return "blog/detail";
	}
}
