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
		return em.createQuery("select b from Blog b order by b.createdDate desc", Blog.class)
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

	public int findAllCnt() {
		return ((Number) em.createQuery("select count(*) from Blog")
					.getSingleResult()).intValue();
	}

	public List<Blog> findListPaging(int startIndex, int pageSize) {
		return em.createQuery("select b from Blog b order by b.createdDate desc", Blog.class)
					.setFirstResult(startIndex)
					.setMaxResults(pageSize)
					.getResultList();
	}

	public int findAllForTagNameCnt(String tagName) {
		return ((Number) em.createQuery("select count(*) "
														+ "from Blog b "
														+ "left join BlogTag bt "
														+ "on b.id = bt.blog "
														+ "left join Tag t "
														+ "on t.id = bt.tag "
														+ "where t.tagName =:tagname")
															.setParameter("tagname", tagName)
															.getSingleResult()).intValue();
	}

	public int findAllForSearchCnt(String keyword) {
		return ((Number) em.createQuery("select count(*) from Blog b where b.title like :keyword")
					.setParameter("keyword", "%" + keyword + "%")	
					.getSingleResult()).intValue();
	}

	public List<Blog> findListPagingForTagName(String tagName, int startIndex, int pageSize) {
		return em.createQuery("select b "
										+ "from Blog b "
										+ "left join BlogTag bt "
										+ "on b.id = bt.blog "
										+ "left join Tag t "
										+ "on t.id = bt.tag "
										+ "where t.tagName =:tagname "
										+ "order by b.createdDate desc", Blog.class)
										.setParameter("tagname", tagName)						
										.setFirstResult(startIndex)
										.setMaxResults(pageSize)
										.getResultList();
	}

	public List<Blog> findListPagingForSearch(String keyword, int startIndex, int pageSize) {
		return em.createQuery("select b from Blog b where b.title like :keyword order by b.createdDate desc", Blog.class)
						.setParameter("keyword", "%" + keyword + "%")
						.setFirstResult(startIndex)
						.setMaxResults(pageSize)
						.getResultList();
	}
}
