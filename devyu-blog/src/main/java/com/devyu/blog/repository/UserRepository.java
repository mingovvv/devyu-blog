package com.devyu.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.devyu.blog.domain.User;

@Repository
public class UserRepository {

	@PersistenceContext
	private EntityManager em;
	
	public User findByUserId(String loginId) {
		
		List<User> user = em.createQuery("select u from User u where u.loginId =:loginId", User.class)
		.setParameter("loginId", loginId)
		.getResultList();

		if(user == null || user.isEmpty()) {
			return null;
		}
		
		return user.get(0);
	}

}
