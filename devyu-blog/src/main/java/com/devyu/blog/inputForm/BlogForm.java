package com.devyu.blog.inputForm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BlogForm {
	private Long id;
	private String title;
	private String contents;
	private String[] tagName;
}
