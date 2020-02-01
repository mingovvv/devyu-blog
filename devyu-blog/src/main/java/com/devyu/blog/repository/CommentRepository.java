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

	public List<Comment> findAllByBlogId(Long id) {
		return em.createQuery("select c from Comment c where blog_id=:id", Comment.class)
					.setParameter("id",id)
					.getResultList();
	}

	public Comment findOne(String id) {
		return em.find(Comment.class, Long.parseLong(id));
	}

	public void delete(Comment comment) {
		em.remove(comment);
	}
}
