
package com.devyu.blog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter 
public class User extends Abstract{

	@Id @GeneratedValue
	@Column(name="user_id")
	private Long id;
	private String loginId;
	private String name;
	private String password;

	@OneToMany(mappedBy = "user")
	private List<Blog> blogs = new ArrayList<>();

}
