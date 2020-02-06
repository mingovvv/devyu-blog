package com.devyu.blog.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.devyu.blog.constant.Constant;
import com.devyu.blog.domain.User;
import com.devyu.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private final UserService userService;

	@GetMapping("login")
	public String loginForm(HttpServletRequest request, Model model) {
		
		try {
			String refererURI = new URI(request.getHeader("referer")).getPath();
			
			System.err.println(refererURI);
			System.err.println(request.getHeader("referer"));
			model.addAttribute("referer", refererURI);
		} catch (URISyntaxException e) {
			logger.info("URISyntaxException error 발생");
			e.printStackTrace();
			return "login/loginForm";
		} catch (NullPointerException e) {
			logger.info("referer값의 null로 인한 NullPointerException 발생 - clinet가 직접 주소창으로 /login를 입력해서 발생함.");
			e.printStackTrace();
			return "login/loginForm";
		}
		return "login/loginForm";
	}
	
	@PostMapping("login")
	public String login(String loginId, String password, Model model, HttpSession session, String referer){
		User user = userService.findByUserId(loginId);
		
		if(user == null || !user.getPassword().equals(password)) { 
			model.addAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "login/loginForm"; 
		}
		
		session.setAttribute(Constant.SESSIONED_ID, user);
		
		return "redirect:"+(referer.isEmpty() ? "/" : referer);
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constant.SESSIONED_ID);
		return "redirect:/";
	}
}
