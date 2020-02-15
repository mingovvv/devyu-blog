package com.devyu.blog;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.devyu.blog.constant.Constant;

@DependsOn
@Configuration
public class ResourceHandlersConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if(Constant.IS_LINUX) {
			registry.addResourceHandler(Constant.RESOURCE_HANDLER)
			.addResourceLocations(Constant.RESOURCE_LOCATIONS_LINUX)
			.setCachePeriod(604800);
		}else {
			registry.addResourceHandler(Constant.RESOURCE_HANDLER)
			.addResourceLocations(Constant.RESOURCE_LOCATIONS_WINDOWS)
			.setCachePeriod(604800);
		}
		
	}
}
