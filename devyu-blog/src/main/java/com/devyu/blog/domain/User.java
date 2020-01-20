package com.devyu.blog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter 
public class User extends Abstract{

	@Id @GeneratedValue
	@Column(name="user_id")
	private Long id;
	private String loginId;
	private String name;
	private String password;
	
	@OneToMany(mappedBy = "user")
	private List<Blog> blogs = new ArrayList<>();
}
