package com.devyu.blog.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.service.BlogService;
import com.devyu.blog.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AboutMeController {

	private final BlogService blogService;
	private final TagService tagService;
	
	@GetMapping("/aboutme")
	public String get(Model model) {
		List<Blog> popList = blogService.findPopList();
		model.addAttribute("popList", popList);
		
		List<String> tagList = tagService.findAllNoDuplicate();
		model.addAttribute("tagList", tagList);
		
		model.addAttribute("active", "aboutMe");
		
		return "about/about";
	}
}
