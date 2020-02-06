package com.devyu.blog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.devyu.blog.inputForm.BlogForm;

import lombok.Getter;

@Entity
@Getter
public class Blog extends Abstract{

	@Id @GeneratedValue
	@Column(name="blog_id")
	private Long id;
	
	@NotNull
	private String title;
	
	@Lob
	private String contents; 
	
	private Long countOfComment;
	private Long countOfThumbsUp;
	private Long countOfViews;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
	private List<BlogTag> blogTags = new ArrayList<>();
	

	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();
	
	
	/* 연관관계 메서드 */
	public void setUser(User userPersistence) {
		this.user = userPersistence;
		user.getBlogs().add(this);
	}
	
	public void addBlogTag(BlogTag blogTag) {
		blogTags.add(blogTag);
		blogTag.setBlog(this);
	}
	
	/* 생성 메서드 */
	public static Blog createBlog(User userPersistence, BlogForm blogForm) {
		Blog blog = new Blog();
		
		//  초기화
		blog.setInit(blogForm);

		// 연관관계[user]
		blog.setUser(userPersistence);
		
		return blog;
	}

	/*  초기화 메서드 */
	public void setInit(BlogForm blogForm) {
		this.title = blogForm.getTitle();
		this.contents = blogForm.getContents();
		this.countOfComment = (long)0;
		this.countOfThumbsUp = (long)0;
		this.countOfViews = (long)0;
	}

	public void addCountOfCommnet() {
		countOfComment = countOfComment+1;
	}
	
	public void addCountOfThumbsUp() {
		countOfThumbsUp = countOfThumbsUp+1;
	}

	public void addCountOfViews() {
		countOfViews = countOfViews+1;
	}

	public void update(BlogForm blogForm) {
		this.title = blogForm.getTitle();
		this.contents = blogForm.getContents();
		this.blogTags.clear();
	}
}
