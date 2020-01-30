package com.devyu.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.Tag;

@Repository
public class TagRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void create(Tag tag) {
		em.persist(tag);
	}

	public List<Tag> findAll() {
		return em.createQuery("select t from Tag t", Tag.class)
					.getResultList();
	}
}
