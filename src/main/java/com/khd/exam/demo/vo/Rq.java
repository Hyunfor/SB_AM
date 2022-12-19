package com.khd.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.khd.exam.demo.util.Utility;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private int loginedMemberId;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp) { // loginedMemberId 검증
		this.req = req;
		this.resp = resp;
		
		this.session= req.getSession();
		
		int loginedMemberId = 0;
		
		if(session.getAttribute("loginedMemberId") != null) { // 로그인 체크
			loginedMemberId = (int) session.getAttribute("loginedMemberId"); // 가져와서 쓰려면 형변환
		}
		
		this.loginedMemberId = loginedMemberId;
		
		this.req.setAttribute("rq", this); // 요청에 rq 키를 세팅
	}

	public void jsPrintHistoryBack(String msg) {
		resp.setContentType("text/html; charset=UTF-8;");
		
		print(Utility.jsHistoryBack(msg));
			
	}
	
	private void print(String str) {
		try {
			resp.getWriter().append(str);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId()); 
	}

	public void logout() {
		session.removeAttribute("loginedMemberId"); // 세션에 저장된 회원번호를 삭제
		
	}

	public String jsReturnOnView(String msg, boolean historyBack) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		
		return "usr/common/js";
	}

	// 해당 메서드는 Rq 객체의 생성을 유도
	// 편의를 위해서 BeforeActionInterceptor에서 호출해줘야 함
	public void initOnBeforeActionInterceptor() {

	}
	
}
