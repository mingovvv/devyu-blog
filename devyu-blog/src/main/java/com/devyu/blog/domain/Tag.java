package com.devyu.blog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Entity
@Getter
public class Tag {
	
	@Id
	@GeneratedValue
	@Column(name = "tag_id")
	private Long id;
	
	@NotNull
	private String tagName;

	@JsonIgnore
	@OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
	private List<BlogTag> blogTags = new ArrayList<>();
	
	/* 연관관계 메서드 */
	public void addBlogTag(BlogTag blogTag) {
		blogTags.add(blogTag);
	}

	public static Tag createTag(String tagName, Blog blog) {
		
		Tag tag = new Tag();
		tag.setInit(tagName);
		
		BlogTag blogTag = new BlogTag();
		blogTag.setBlog(blog);
		blogTag.setTag(tag);
		
		tag.addBlogTag(blogTag);
		blog.addBlogTag(blogTag);
		
		return tag;
	}

	private void setInit(String tagName) {
		this.tagName=tagName;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", tagName=" + tagName + "]";
	}

	
}
