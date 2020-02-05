package com.devyu.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.BlogTag;
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

	public List<Blog> findBlogForTagName(String tagname) {
		return em.createQuery("select b "
				+ "from Blog b "
				+ "left join BlogTag bt "
				+ "on b.id = bt.blog "
				+ "left join Tag t "
				+ "on t.id = bt.tag "
				+ "where t.tagName =:tagname", Blog.class)
					.setParameter("tagname", tagname)
					.getResultList();
	}

	public void delete(Tag tag) {
		em.remove(tag);
	}

	public Tag findOne(Long id) {
		return em.find(Tag.class, id);
	}

}
