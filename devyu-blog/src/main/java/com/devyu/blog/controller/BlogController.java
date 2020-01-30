package com.devyu.blog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.UploadContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.Comment;
import com.devyu.blog.domain.User;
import com.devyu.blog.inputForm.BlogForm;
import com.devyu.blog.service.BlogService;
import com.devyu.blog.service.CommentService;
import com.devyu.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BlogController {
	
	private final UserService userService;
	private final BlogService blogService;
	private final CommentService commentService;
	

	@GetMapping("/blog")
	public String blogList(Model model) {
		List<Blog> blogList = blogService.findAll();
		model.addAttribute("blogList", blogList);
		return "blog/list";
	}
	
	@GetMapping("/blog/{id}")
	public String detail(@PathVariable Long id, Model model, HttpServletRequest req, HttpServletResponse res) {
		
		boolean isCookie=false;
		Cookie[] cookies = req.getCookies();
		if(cookies!=null){   
			for(int i=0;i<cookies.length;i++) {
				if(cookies[i].getName().equals(String.valueOf(id))) {
					isCookie=true;
				}
			}
		}
		if(!isCookie) {
			Cookie cookie = new Cookie(String.valueOf(id), String.valueOf(id)); 
			cookie.setMaxAge(60*5); // 5 min
			res.addCookie(cookie); 
		}
		
		Blog blog = blogService.findOne(id, isCookie);
		User user = userService.findOne(blog.getUser().getId());
		List<Comment> comments = commentService.findAllByBlogId(id);
		model.addAttribute("blog", blog);
		model.addAttribute("user", user);
		model.addAttribute("comments", comments);
		
		
		return "blog/detail";
	}
	
	@GetMapping("/blog/create")
	public String createForm() {
		return "blog/createForm";
	}
	
	@PostMapping("/blog/create")
	public String create(HttpServletRequest req, BlogForm blogForm, HttpSession session) {
		
		blogService.create(blogForm, session, req);
		
		return "redirect:/blog";
	}
	
	@ResponseBody
	@PostMapping("/blog/{id}/like")
	public Long countOfThumbsUp(@PathVariable Long id) {
		return blogService.findCountOfThumbsUp(id);
	}
	
	@ResponseBody
	@PostMapping("/blog/imageUpload")
	public String createFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		
		String filePath = "C:\\Users\\min\\Desktop\\upload-folder";
		File newFile = new File(filePath);
	    if(!newFile.exists()) {
	    	newFile.mkdirs();
	    }
	    
		String originalFilename = file.getOriginalFilename();
		
		InputStream fis =  file.getInputStream();
		FileOutputStream fos = new FileOutputStream(filePath + File.separator + originalFilename); //File.separator 구분자 / \ 윈도우는 \ 유닉스는 / 니깐 둘중 골라주는놈 파일.세퍼레이톨

		byte[] buf = new byte[1024]; //버퍼 만들기
			
		int size = 0;
		
		while((size = fis.read(buf,0,1024)) != -1)
				fos.write(buf,0,size);
		
		fis.read(buf, 0, 1024);
		
		
		fis.close();
		fos.close();
		
		return "/blog/images/"+originalFilename;
	}
}
