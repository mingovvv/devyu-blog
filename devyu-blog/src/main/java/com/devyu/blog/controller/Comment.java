package com.devyu.blog.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

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
	
	
}
