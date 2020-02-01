package com.devyu.blog;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.devyu.blog.domain.User;
import com.devyu.blog.repository.UserRepository;
import com.devyu.blog.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginControllerTest {
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void 로그인_비밀번호_테스트() {
		User user = userService.findByUserId("devyu");
		
		if(user == null) {
			System.err.println("아이디가 다르다");
		}
		
		if(!user.getPassword().equals("jang82")) { 
			System.err.println("패스워드가 다르다");
		}
		
	}
	
	@Test
	public void 세션_중복_테스트(HttpSession session) {
		User user1 = new User();
		user1.setId((long) 0);
		user1.setName("민규");
		
		User user2 = new User();
		user2.setId((long) 0);
		user2.setName("상진");
		
		session.setAttribute("userInfo", user1);
		session.setAttribute("userInfo", user2);
		
		System.out.println(session.getAttribute("userInfo"));
	}
}
