package com.devyu.blog;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import com.devyu.blog.constant.Constant;

@Component
public class ArgsConfig {
	public ArgsConfig(ApplicationArguments applicationArguments) {
		if (applicationArguments.containsOption("linux")) {
			Constant.IS_LINUX = true;
		} else {
			Constant.IS_LINUX = false;
		}
	}
}
