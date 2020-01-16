package com.devyu.blog.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.ToString;

@Entity
@Getter @ToString
public class Blog {
	
	@Id @GeneratedValue
	@Column(name="blog_id")
	private Long id;
	
	private String title;
	
	@Lob
	private String contents; 
	
	private Long countOfComment;
	private Long countOfThumbsUp;
	private Long countOfViews;
	
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "blog")
	private List<Tags> tags = new ArrayList<>(); 
	
}
