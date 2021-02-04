package com.github.rumoel.hub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");

		registry.addViewController("/register").setViewName("register");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/profile").setViewName("profile");

		registry.addViewController("/about").setViewName("about");
		registry.addViewController("/vkBots").setViewName("vkBots");

		registry.addViewController("/honeypots").setViewName("honeypots");

	}
}