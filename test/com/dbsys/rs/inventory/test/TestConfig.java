package com.dbsys.rs.inventory.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.dbsys.rs.inventory.ApplicationConfig;

@Configuration
@ComponentScan("com.dbsys.rs.inventory.controller")
@EnableWebMvc
@Import(ApplicationConfig.class)
public class TestConfig {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
}
