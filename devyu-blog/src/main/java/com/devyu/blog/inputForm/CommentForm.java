package com.devyu.blog.inputForm;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentForm {
	private String name;
	private String password;
	private String contents;
	private Boolean isWriter;
}
