package com.devyu.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.devyu.blog.domain.Blog;

@Repository
public class BlogRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void create(Blog blog) {
		em.persist(blog);
	}

	public List<Blog> findAll() {
		return em.createQuery("select b from Blog b", Blog.class)
				.getResultList();
	}

	public Blog findOne(Long id) {
		return em.find(Blog.class, id);
	}

	public List<Blog> findPopList() {
		return em.createQuery("select b from Blog b order by b.countOfViews desc", Blog.class)
				.setMaxResults(3)
				.getResultList();
	}

	public List<Blog> findAllSearchText(String keyword) {
		return em.createQuery("select b from Blog b where b.title like :keyword", Blog.class)
				.setParameter("keyword", "%" + keyword + "%")
				.getResultList();
	}

}
