package com.devyu.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class BlogTag {

	@Id
	@GeneratedValue
	@Column(name = "blog_tag_id")
	private Long id;
	
	private Blog blog;
	private Tag tag;
	
}
