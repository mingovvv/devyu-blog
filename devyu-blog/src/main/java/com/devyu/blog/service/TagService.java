package com.devyu.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devyu.blog.domain.Blog;
import com.devyu.blog.domain.Tag;
import com.devyu.blog.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

	private final TagRepository tagRepository;
	

	public ArrayList<String> findAllNoDuplicate() {
		
		List<Tag> findAll = tagRepository.findAll();

		ArrayList<String> tagNames = new ArrayList<String>();
		for(int i=0;i<findAll.size();i++) {
			tagNames.add(findAll.get(i).getTagName());
		}
		TreeSet<String> tagNameNoDuplicationTreeSet = new TreeSet<String>(tagNames);
		ArrayList<String> tagNameNoDuplicationArrayList = new ArrayList<String>(tagNameNoDuplicationTreeSet);
		return tagNameNoDuplicationArrayList;
	}


	public List<Blog> findBlogForTagName(String tagname) {
		return tagRepository.findBlogForTagName(tagname);
	}
}
