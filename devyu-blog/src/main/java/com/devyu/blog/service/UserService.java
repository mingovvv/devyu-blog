package com.devyu.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devyu.blog.domain.User;
import com.devyu.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	
	private final UserRepository userRepository;
	
	@Transactional
	public User findByUserId(String loginId) {
		return  userRepository.findByUserId(loginId);
	}
	
}
