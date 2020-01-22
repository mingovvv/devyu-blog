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

import com.devyu.blog.inputForm.BlogForm;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
public class Blog extends Abstract{

	@Id @GeneratedValue
	@Column(name="blog_id")
	private Long id;
	
	private String title;
	
	@Lob
	private String contents; 
	
	private Long countOfComment;
	private Long countOfThumbsUp;
	private Long countOfViews;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@JsonIgnore
	@JsonBackReference
	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
	private List<Tag> tags = new ArrayList<>();
	

	@JsonBackReference
	@OneToMany(mappedBy = "blog")
	private List<Comment> comments = new ArrayList<>();
	
	
	/* 연관관계 메서드 */
	public void setUser(User user) {
		this.user = user;
		user.getBlogs().add(this);
	}
	
	/* 생성 메서드 */
	public static Blog createBlog(User user, BlogForm blogForm) {
		Blog blog = new Blog();
		
		//  초기화
		blog.setInit(blogForm);

		// 연관관계
		blog.setUser(user);
		
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
	
}
