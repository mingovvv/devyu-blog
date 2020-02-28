package com.devyu.blog.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.Comment;
import com.devyu.blog.inputForm.CommentForm;
import com.devyu.blog.repository.BlogRepository;
import com.devyu.blog.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final BlogRepository blogRepository;
	
	@Transactional
	public Comment create(Long id, CommentForm commentForm) {
		
		// 영속성 컨텍스트의 1차캐시 안에 Blog객체 생성
		Blog blogJPA = blogRepository.findOne(id);
		
		// comment count + 1
		blogJPA.addCountOfCommnet();
		
		// 코멘트 생성
		Comment comment = Comment.createComment(blogJPA, commentForm);
		
		// 코멘트 저장
		commentRepository.create(comment);
		return comment;
	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	public List<Comment> findAllByBlogId(Long id) {
		return commentRepository.findAllByBlogId(id);
	}

	@Transactional
	public Boolean checkPassword(HashMap<String, String> jsonMap) {
		String id = jsonMap.get("id");
		String inputPassword = jsonMap.get("password");
		String auth =  jsonMap.get("auth");
		String blogId = jsonMap.get("blogId");
		
		Comment comment = commentRepository.findOne(id);

		// 로그인을 통해 인증이된 유저는 댓글 password를 입력하지 않음.
		if(auth=="ok") {
			
			// delete
			commentRepository.delete(comment);
			
			// commnet count -1
			Blog blog = blogRepository.findOne(Long.valueOf(blogId));
			blog.removeCountOfCommnet();
			return true;
			
		// 비회원의 경우 댓글을 달때, password를 입력.
		}else {
			// checking
			Boolean result = comment.equalsPassword(comment.getPassword(), inputPassword);
			
			// delete
			if(result) {
				commentRepository.delete(comment);
				
				// commnet count -1
				Blog blog = blogRepository.findOne(Long.valueOf(blogId));
				blog.removeCountOfCommnet();
			}
			return result;
		}
	}

	public List<Comment> findLatestComment() {
		return commentRepository.findLatestComment();
	}
}
