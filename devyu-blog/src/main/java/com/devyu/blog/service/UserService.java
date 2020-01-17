package com.devyu.blog.service;

import org.springframework.stereotype.Service;

import com.devyu.blog.domain.User;
import com.devyu.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public User findByUserId(String loginId) {
		return  userRepository.findByUserId(loginId);
	}
	
}
