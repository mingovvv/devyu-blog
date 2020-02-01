package com.devyu.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.service.BlogService;
import com.devyu.blog.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TagController {

	private final TagService tagService;
	private final BlogService blogService;
	
	@GetMapping("/blog/search={tagName}")
	public String tagSearch(@PathVariable String tagName, Model model) {
		List<Blog> blogforTagName = tagService.findBlogForTagName(tagName);
		model.addAttribute("blogList", blogforTagName);

		List<Blog> popList = blogService.findPopList();
		model.addAttribute("popList", popList);
		
		List<String> tagList = tagService.findAllNoDuplicate();
		model.addAttribute("tagList", tagList);
		
		Map<String, String> blogFlag = new HashMap<>();
		blogFlag.put("flag", "searchTag");
		blogFlag.put("tagName", tagName);
		model.addAttribute("blogFlag", blogFlag);
		
		return "blog/list";
	}
}
