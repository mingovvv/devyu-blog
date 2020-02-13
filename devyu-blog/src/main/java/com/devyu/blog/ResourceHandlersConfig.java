package com.devyu.blog;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.devyu.blog.constant.Constant;

@Configuration
public class ResourceHandlersConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(Constant.RESOURCE_HANDLER)
					.addResourceLocations(Constant.RESOURCE_LOCATIONS)
					.setCachePeriod(31536000);
	}
}
