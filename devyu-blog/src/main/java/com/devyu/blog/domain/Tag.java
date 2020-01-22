package com.devyu.blog.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.ToString;

@Entity
@Getter @ToString
public class Tag {
	
	@Id
	@GeneratedValue
	@Column(name = "tag_id")
	private Long id;
	
	private String tagName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="blog_id")
	private Blog blog;
	
	
	/* 연관관계 메서드 */
	public void setBlog(Blog blog) {
		this.blog = blog;
		blog.getTags().add(this);
	}
	
	/* 생성 메서드 */
	public static Tag createTag(String tags, Blog blog) {
		Tag tag = new Tag(); 
		
		// 연관관계 메서드 주입
		tag.setBlog(blog);
		
		//  초기화 메서드 주입
		tag.setInit(tags);
			
		return tag;
	}

	private void setInit(String tags) {
		this.tagName = tags;
	}
}
