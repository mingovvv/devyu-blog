package com.devyu.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.Tag;

@Service
@Transactional(readOnly = true)
public class TagService {
	
	@Transactional
	public void create(String[] tags, Blog blog) {
		for(int i=0;i<tags.length;i++) {
			if(tags[i] != "") {
				Tag.createTag(tags[i].trim(), blog);
			}
		}
	}
}
