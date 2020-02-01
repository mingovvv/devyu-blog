package com.devyu.blog.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Abstract {

	@CreatedDate
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	
	public String getFormattedCreatedDate() {
		return getFormattedDate(createdDate,"yyyy-MM-dd HH:mm:ss");
	}
	
	public String getFormattedModifiedDate() {
		return getFormattedDate(modifiedDate,"yyyy-MM-dd HH:mm:ss");
	}
	
	private String getFormattedDate(LocalDateTime date, String format) {
		if (date == null) {
			return "";
		}
		return date.format(DateTimeFormatter.ofPattern(format));
	}
}
