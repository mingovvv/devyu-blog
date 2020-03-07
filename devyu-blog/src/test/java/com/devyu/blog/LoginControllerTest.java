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
	public void login_password_test() {
		
	}
}
