package com.devyu.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String Home(Model model) {
		model.addAttribute("active", "home");
		return "index";
	}
	
}
