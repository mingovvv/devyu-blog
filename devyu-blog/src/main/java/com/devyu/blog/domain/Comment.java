package com.devyu.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.devyu.blog.inputForm.CommentForm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
public class Comment extends Abstract{
	
	@Id
	@GeneratedValue
	@Column(name = "comment_id")
	private Long id;
	
	private String name;
	
	@Lob
	private String contents;
	
	private String password;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="blog_id")
	private Blog blog;
	
	/* 생성 메서드 */
	public static Comment createComment(Blog blog, CommentForm commentForm) {
		Comment comment = new Comment();
		
		// 초기화
		comment.setInit(commentForm);
		
		// 연관관계
		comment.setBlog(blog);
		
		return comment;
	}
	
	/* 연관관계  */
	private void setBlog(Blog blog) {
		this.blog = blog;
		blog.getComments().add(this);
	}

	/*  초기화 메서드 */
	private void setInit(CommentForm commentForm) {
		this.name = commentForm.getName();
		this.password = commentForm.getPassword();
		this.contents = commentForm.getContents();
	}
}
