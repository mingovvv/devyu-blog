package com.devyu.blog.inputForm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ContactForm {

	private String name;
	private String phone;
	private String eMail;
	private String message;
}
