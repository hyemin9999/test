package com.woori.codenova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.woori.codenova.repository.CategoryRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new CategoryInterceptor(categoryRepository)).addPathPatterns("/**")
				.excludePathPatterns("/.well-known/**", "/error/**", "/api/words/**", "/admin/**", "/css/**", "/js/**");

//		registry.addInterceptor(new CategoryInterceptor(categoryRepository)).addPathPatterns("/board", "/notice")
//				.excludePathPatterns("/login", "/admin"); // 특정 경로는 제외
	}
}