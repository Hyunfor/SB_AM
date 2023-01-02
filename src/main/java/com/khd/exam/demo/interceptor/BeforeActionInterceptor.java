package com.khd.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.khd.exam.demo.vo.Rq;

@Component 
public class BeforeActionInterceptor implements HandlerInterceptor{ // 컨트롤러 보다 먼저 실행 됨.
	
	private Rq rq;
	
	@Autowired
	public BeforeActionInterceptor(Rq rq) {
	this.rq = rq;
	}

	@Override				// 공유 자원을 쓸 수 있게 해줌
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
			throws Exception { 
		
		req.setAttribute("rq", rq);
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
	
	
}
