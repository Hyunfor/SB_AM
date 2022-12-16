package com.khd.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.khd.exam.demo.interceptor.BeforeActionInterceptor;
import com.khd.exam.demo.interceptor.NeedLoginInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer{
	// 인터셉터를 한번 무조건 구동시킴

	private BeforeActionInterceptor beforeActionInterceptor;
	private NeedLoginInterceptor needLoginInterceptor;
	
	@Autowired 
	public MyWebMvcConfigurer(BeforeActionInterceptor beforeActionInterceptor, NeedLoginInterceptor needLoginInterceptor){
		this.beforeActionInterceptor = beforeActionInterceptor;
		this.needLoginInterceptor = needLoginInterceptor;
	}

	@Override // 레지스트리에 한번 등록
	public void addInterceptors(InterceptorRegistry registry) {  // **(모두) ~ 경로 패턴을 한번 가져옴            
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**").excludePathPatterns("/error");
		
		// 요청이 들어오면 로그인 여부 확인
		registry.addInterceptor(needLoginInterceptor).addPathPatterns("/usr/article/write")
		.addPathPatterns("/usr/article/doWrite").addPathPatterns("/usr/article/doDelete")
		.addPathPatterns("/usr/article/modify").addPathPatterns("/usr/article/doModify");
	}
	
	
	
}
