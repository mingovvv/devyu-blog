package com.devyu.blog.controller;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.devyu.blog.constant.Constant;
import com.devyu.blog.domain.Blog;
import com.devyu.blog.inputForm.ContactForm;
import com.devyu.blog.service.BlogService;
import com.devyu.blog.service.CommentService;
import com.devyu.blog.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ContactController {
	
	private final BlogService blogService;
	private final TagService tagService;

	@GetMapping("/contact")
	public String contact(Model model) {
		List<Blog> popList = blogService.findPopList();
		model.addAttribute("popList", popList);
		
		List<String> tagList = tagService.findAllNoDuplicate();
		model.addAttribute("tagList", tagList);
		return "contact/contact";
	}
	
	@PostMapping("/contact")
	public String mailSender(ContactForm contactForm) throws AddressException, MessagingException{
		
		String subject = contactForm.getName()+Constant.SMTP_TITLE; 
		String body =   
			"<div><b>이름</b> : "+	contactForm.getName()	+ "</div>" +
			"<div><b>이메일</b> : "+	contactForm.getEMail()	+ "</div>" +
			"<div><b>넘버</b> : "+	contactForm.getPhone()	+ "</div>" +
			"<div><b>메세지</b> : "+	contactForm.getMessage() + "</div>";
		
		// 정보를 담기 위한 객체 생성 // SMTP 서버 정보 설정
		Properties props = System.getProperties();  
		props.put("mail.smtp.host", Constant.SMTP_HOST); 
		props.put("mail.smtp.port", Constant.SMTP_PORT); 
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.ssl.enable", "true"); 
		props.put("mail.smtp.ssl.trust", Constant.SMTP_HOST); 
		
		//Session 생성 
		Session session = Session.getInstance(props,  new javax.mail.Authenticator() {
		protected javax.mail.PasswordAuthentication getPasswordAuthentication() { 
			return new javax.mail.PasswordAuthentication(Constant.SMTP_USERNAME, Constant.SMTP_PASSWORD); 
			} 
		}); 
		
		session.setDebug(true); //for debug 
		
		Message mimeMessage = new MimeMessage(session); //MimeMessage 생성 
		mimeMessage.setFrom(new InternetAddress(Constant.SMTP_USERNAME)); //발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요. 
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(Constant.SMTP_RECIPIENT)); //수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음 
		mimeMessage.setSubject(subject); //제목셋팅 
		mimeMessage.setText(body); //내용셋팅 
		mimeMessage.setContent(body, "text/html; charset=UTF-8"); 
		Transport.send(mimeMessage); //javax.mail.Transport.send() 이용

		return "contact/contact";
	}
}
