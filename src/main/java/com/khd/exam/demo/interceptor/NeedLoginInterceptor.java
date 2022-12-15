package com.khd.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.khd.exam.demo.vo.Rq;

@Component 
public class NeedLoginInterceptor implements HandlerInterceptor { // 인터셉터에서 로그인 여부 체크
	@Override				// 공유 자원을 쓸 수 있게 해줌
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
			throws Exception { 
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		if(rq.getLoginedMemberId() == 0) { // rq 객체에 값이 씌워지지 못 한 경우 . 0 일경우 세션에 아무것도 없다.
			
			rq.jsPrintHistoryBack("로그인 후 이용해주세요.");
			return false;
		}
		
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}
