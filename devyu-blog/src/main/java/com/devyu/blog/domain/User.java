package com.devyu.blog.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.ToString;

@Entity
@Getter @ToString
public class User{

	@Id @GeneratedValue
	@Column(name="user_id")
	private Long id;
	private String loginId;
	private String name;
	private String password;
	
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	@OneToMany(mappedBy = "user")
	private List<Blog> blogs = new ArrayList<>();
}
