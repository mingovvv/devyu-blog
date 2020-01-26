package com.devyu.blog.service;

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
	
	
	
}
