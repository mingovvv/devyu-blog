package com.devyu.blog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ejb.access.EjbAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.devyu.blog.constant.Constant;
import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.Comment;
import com.devyu.blog.domain.Pagination;
import com.devyu.blog.domain.User;
import com.devyu.blog.inputForm.BlogForm;
import com.devyu.blog.repository.BlogRepository;
import com.devyu.blog.service.BlogService;
import com.devyu.blog.service.CommentService;
import com.devyu.blog.service.TagService;
import com.devyu.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BlogController {
	
	private final UserService userService;
	private final BlogService blogService;
	private final TagService tagService;
	private final CommentService commentService;
	

	@RequestMapping(value = {	"/blog", "/blog/page",
												"/blog/search", 
												"/blog/tag"})
	public String blogList(Model model, 
									@RequestParam(defaultValue="1") int curPage,
									@RequestParam(defaultValue="1") String tagname,
									@RequestParam(defaultValue="1") String keyword,
									HttpSession session,
									HttpServletRequest request,
									HttpServletResponse response) {
		
		String uri = request.getRequestURI(); 

		int listCnt=0;
		Pagination pagination = null;
		List<Blog> blogList = null;
		List<Blog> popList = null;
		List<String> tagList = null;
		Map<String, Object> blogFlag = new HashMap<>();
		
		switch(uri) {
		case "/blog" : 
		case "/blog/page" :
			listCnt = blogService.findAllCnt();
			pagination = new Pagination(listCnt, curPage);
			blogList = blogService.findListPaging(pagination.getStartIndex(), pagination.getPageSize());
			blogFlag.put("flag", "default");
			blogFlag.put("keyword", null);
			blogFlag.put("count", listCnt);
			break;
		case "/blog/tag" :
			listCnt = blogService.findAllForTagNameCnt(tagname);
			pagination = new Pagination(listCnt, curPage);
			blogList = blogService.findListPagingForTagName(tagname, pagination.getStartIndex(), pagination.getPageSize());
			blogFlag.put("flag", "searchTag");
			blogFlag.put("keyword", null);
			blogFlag.put("tagname", tagname);
			blogFlag.put("count", listCnt);
			break;
		case "/blog/search" :
			listCnt = blogService.findAllForSearchCnt(keyword);
			pagination = new Pagination(listCnt, curPage);
			blogList = blogService.findListPagingForSearch(keyword, pagination.getStartIndex(), pagination.getPageSize());
			blogFlag.put("flag", "searchText");
			blogFlag.put("keyword", keyword);
			blogFlag.put("count", listCnt);
			break;
		
		default :

		}
		popList = blogService.findPopList();
		tagList = tagService.findAllNoDuplicate();
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("blogList", blogList);
		model.addAttribute("popList", popList);
		model.addAttribute("tagList", tagList);
		model.addAttribute("blogFlag", blogFlag);
		model.addAttribute("active", "blog");
		
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
		List<Blog> popList = blogService.findPopList();
		List<String> tagList = tagService.findAllNoDuplicate();
		model.addAttribute("tagList", tagList);
		model.addAttribute("popList", popList);
		model.addAttribute("blog", blog);
		model.addAttribute("user", user);
		model.addAttribute("comments", comments);
		model.addAttribute("active", "blog");
		
		
		return "blog/detail";
	}
	
	@GetMapping("/blog/create")
	public String createForm(Model model, HttpSession session) {
		
		if(session.getAttribute(Constant.SESSIONED_ID) == null) {
			model.addAttribute("errorMessage", "로그인된 사용자만 이용할 수 있습니다.");
			return "login/loginForm";
		}
		
		List<Blog> popList = blogService.findPopList();
		model.addAttribute("popList", popList);
		
		List<String> tagList = tagService.findAllNoDuplicate();
		model.addAttribute("tagList", tagList);
		
		model.addAttribute("active", "blog");
		return "blog/createForm";
	}
	
	@PostMapping("/blog/create")
	public String create(BlogForm blogForm, HttpSession session) {
		
		blogService.create(blogForm, session);
		
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
	    
	    String uuid = UUID.randomUUID().toString();
		String originalFilename = file.getOriginalFilename();
		
		InputStream fis =  file.getInputStream();
		FileOutputStream fos = new FileOutputStream(filePath + File.separator + uuid + originalFilename); //File.separator 구분자 / \ 윈도우는 \ 유닉스는 / 니깐 둘중 골라주는놈 파일.세퍼레이톨

		byte[] buf = new byte[1024]; //버퍼 만들기
			
		int size = 0;
		
		while((size = fis.read(buf,0,1024)) != -1)
				fos.write(buf,0,size);
		
		fis.read(buf, 0, 1024);
		
		
		fis.close();
		fos.close();
		
		return "/blog/images/"+uuid + originalFilename;
	}
	
	@PostMapping("/blog/searchText")
	public String searchText(String keyword, Model model) {
		List<Blog> searchList = blogService.findAllSearchText(keyword);
		model.addAttribute("blogList",searchList);
		
		List<Blog> popList = blogService.findPopList();
		model.addAttribute("popList", popList);
		
		List<String> tagList = tagService.findAllNoDuplicate();
		model.addAttribute("tagList", tagList);
		
		Map<String, Object> blogFlag = new HashMap<>();
		blogFlag.put("flag", "searchText");
		blogFlag.put("keyword", keyword);
		blogFlag.put("count", searchList.size());
		model.addAttribute("blogFlag", blogFlag);
		
		return "blog/list";
		
	}
	
	@GetMapping("/blog/update/{id}")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session, HttpServletRequest request) {
		
		Blog blog = blogService.findOne(id);
		
		if(session.getAttribute(Constant.SESSIONED_ID) == null) {
			model.addAttribute("errorMessage", "로그인된 사용자만 이용할 수 있습니다.");
			return "login/loginForm";
		}
		
		User user = (User) session.getAttribute(Constant.SESSIONED_ID); 
		
		if(!user.getId().equals(blog.getUser().getId())) {
			Map<String, Object> modal = new HashMap<>();
			modal.put("title", "malicious Access");
			modal.put("message", "로그인된 유저의 게시글이 아닙니다. 본인의 게시물만 수정할 수 있습니다.");
			model.addAttribute("modal", modal);
			return "index";
		}
		
		List<Blog> popList = blogService.findPopList();
		List<String> tagList = tagService.findAllNoDuplicate();
		model.addAttribute("blog", blog);
		model.addAttribute("tagList", tagList);
		model.addAttribute("popList", popList);
		model.addAttribute("active", "blog");
		
		return "blog/updateForm";
	}
	
	@PostMapping("/blog/update/{id}")
	public String update(@PathVariable Long id, BlogForm blogForm) {
		blogService.update(id, blogForm);
		return "redirect:/blog";
	}
	
	@PostMapping("/blog/delete/{id}")
	public String delete(@PathVariable Long id, HttpSession session) {
		blogService.delete(id);
		return "redirect:/blog";
	}
}

