package com.devyu.blog.domain;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Entity
@Getter
public class BlogTag {

	@Id
	@GeneratedValue
	@Column(name = "blog_tag_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="blog_id")
	private Blog blog;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="tag_id")
	private Tag tag;

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
}
