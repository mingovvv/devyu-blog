package com.devyu.blog.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devyu.blog.constant.Constant;
import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.Tag;
import com.devyu.blog.domain.User;
import com.devyu.blog.inputForm.BlogForm;
import com.devyu.blog.repository.BlogRepository;
import com.devyu.blog.repository.TagRepository;
import com.devyu.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlogService {
	
	private final BlogRepository blogRepository;
	private final UserRepository userRepository;
	private final TagRepository tagRepository;
	
	@Transactional
	public Blog create(BlogForm blogForm, HttpSession session, HttpServletRequest req) {

		// session에 담아둔 login user의 정보 가져오기
		User user = (User)session.getAttribute(Constant.SESSIONED_ID);
		
		// 영속성 컨텍스트 1차캐시에 User 객체를 생성
		User userPersistence = userRepository.findOne(user.getId());
		
		Blog blog = Blog.createBlog(userPersistence, blogForm);
		
		String[] tags = req.getParameterValues("tagName");
		for(int i=0;i<tags.length;i++) {
			if(tags[i].trim() != "") {
				Tag tag = Tag.createTag(tags[i].trim(), blog);
				
				// 태그 저장
				tagRepository.create(tag);
			}
			
		}
		
		
		// 블로그 저장
		blogRepository.create(blog);
		
		return blog;
	}
	
	public List<Blog> findAll() {
		return blogRepository.findAll();
	}

	public Blog findOne(Long id) {
		return blogRepository.findOne(id);
	}
}
