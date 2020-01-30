package com.devyu.blog;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandlersConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/blog/images/**")
					.addResourceLocations("file:///C:/Users/min/Desktop/upload-folder/")
					.setCachePeriod(31536000);
	}
}
