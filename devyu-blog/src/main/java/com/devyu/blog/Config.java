package com.devyu.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class Config {
	
	// thymeleaf layout
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}
}
