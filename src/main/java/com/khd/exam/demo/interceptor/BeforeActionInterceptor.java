package com.khd.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.khd.exam.demo.vo.Rq;

@Component 
public class BeforeActionInterceptor implements HandlerInterceptor{ // 컨트롤러 보다 먼저 실행 됨.

	@Override				// 공유 자원을 쓸 수 있게 해줌
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
			throws Exception { 
		
		Rq rq = new Rq(req);
		req.setAttribute("rq", rq); // 요청된 Rq를 인터셉터와 컨트롤러도 같이 사용함.(공유 자원느낌)
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
	
	
}
