package com.devyu.blog.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devyu.blog.constant.Constant;
import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.User;
import com.devyu.blog.inputForm.BlogForm;
import com.devyu.blog.repository.BlogRepository;
import com.devyu.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlogService {
	
	private final BlogRepository blogRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Blog create(BlogForm blogForm, HttpSession session) {
		
		// session에 담아둔 login user의 정보 가져오기
		User user = (User)session.getAttribute(Constant.SESSIONED_ID);
		
		// 영속성 유지를 위한 jpa select
		User userJPA = userRepository.findOne(user.getId());
		
		// 블로그 생성
		Blog blog = Blog.createBlog(userJPA, blogForm);
		
		// 블로그 저장
		blogRepository.create(blog);
		return blog;
	}

	public List<Blog> findAll() {
		return blogRepository.findAll();
	}
}
