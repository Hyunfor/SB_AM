package com.khd.exam.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.khd.exam.demo.interceptor.BeforeActionInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer{
	// 인터셉터를 한번 무조건 구동시킴

	private BeforeActionInterceptor beforeActionInterceptor;
	
	@Autowired 
	public MyWebMvcConfigurer(BeforeActionInterceptor beforeActionInterceptor){
		this.beforeActionInterceptor = beforeActionInterceptor;
	}

	@Override // 레지스트리에 한번 등록
	public void addInterceptors(InterceptorRegistry registry) {  // **(모두) ~ 경로 패턴을 한번 가져옴            
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**").excludePathPatterns("/error");
																							// 경로 제외			
	}
	
	
	
}
