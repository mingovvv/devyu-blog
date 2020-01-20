package com.devyu.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.ToString;

@Entity
@Getter @ToString
public class Comment {
	
	@Id
	@GeneratedValue
	@Column(name = "comment_id")
	private Long id;
	
	private String name;
	
	@Lob
	private String contents;
	
	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="blog_id")
	private Blog blog;
}
