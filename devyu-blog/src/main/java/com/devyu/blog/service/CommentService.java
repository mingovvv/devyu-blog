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
		
		// 영속성 유지를 위한 jpa select
		Blog blogJPA = blogRepository.findOne(id);
		
		// 코멘트 생성
		Comment comment = Comment.createComment(blogJPA, commentForm);
		
		// 코멘트 저장
		commentRepository.create(comment);
		return comment;
	}

	public List<Comment> findAll() {
		return commentRepository.findAll();
	}
	
	
	
}
