package com.devyu.blog.constant;

public class Constant {
	
	// SESSION
	public static final String SESSIONED_ID = "SESSIONED_ID";
	
	// SMTP
	public static final String SMTP_HOST = "smtp.naver.com";
	public static final int SMTP_PORT = 465;;
	public static final String SMTP_USERNAME = "devyu-blog@naver.com";
	public static final String SMTP_PASSWORD = "KSUUL97TYSYP";
	public static final String SMTP_TITLE = "님께서 메일을 보내셨습니다.";
	public static final String SMTP_RECIPIENT = "puregyu@naver.com";
	
	// modal
	public static final String MAIL_SUCCESS_MESSAGE = "성공적으로 메일이 전송되었습니다. :)";
	public static final String MALICIOUS_ACCESS_MESSAGE = "로그인된 유저의 게시글이 아닙니다. 본인의 게시물만 수정할 수 있습니다.";
	
	// error message(login-form)
	public static final String MALICIOUS_CREATE_MESSAGE = "로그인된 사용자만 이용할 수 있습니다.";
	public static final String ACCOUNT_ACCESS_ERROR_MESSAGE = "아이디 또는 비밀번호가 일치하지 않습니다.";
	
	// image file storage
	public static final String RESOURCE_HANDLER = "/blog/images/**";
	
	public static Boolean IS_LINUX = false;
	
	// windos
	public static final String IMAGE_FILE_PATH_WINDOWS = "C:\\Users\\min\\upload-folder";
	public static final String RESOURCE_LOCATIONS_WINDOWS = "file:///C:/Users/min/upload-folder/";
	
	// linux
	public static final String IMAGE_FILE_PATH_LINUX = "/home/ubuntu/upload-folder";
	public static final String RESOURCE_LOCATIONS_LINUX = "file:///home/ubuntu/upload-folder/";
}
