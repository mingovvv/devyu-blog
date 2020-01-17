package com.devyu.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.devyu.blog.Constant.Constant;
import com.devyu.blog.domain.User;
import com.devyu.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final UserService userService;

	@GetMapping("login")
	public String loginForm() {
		return "login/loginForm";
	}
	
	@PostMapping("login")
	public String login(String loginId, String password, Model model, HttpSession session){
		
		User user = userService.findByUserId(loginId);
		
		if(user == null || !user.getPassword().equals(password)) { 
			model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "login/loginForm"; 
		}
		
		session.setAttribute(Constant.SESSIONED_ID, user);
		
		return "redirect:/";
	}
	
	@PostMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constant.SESSIONED_ID);
		return "redirect:/";
	}
}
