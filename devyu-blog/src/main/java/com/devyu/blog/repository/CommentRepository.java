package com.devyu.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.devyu.blog.domain.Comment;

@Repository
public class CommentRepository {

	@PersistenceContext
	private EntityManager em;

	public void create(Comment comment) {
		em.persist(comment);
	}

	public List<Comment> findAll() {
		return em.createQuery("select c from Comment c", Comment.class)
					.getResultList();
	}
}
