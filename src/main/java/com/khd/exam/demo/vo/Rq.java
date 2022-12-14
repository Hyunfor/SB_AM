package com.khd.exam.demo.vo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.Getter;

public class Rq {
	@Getter
	private int loginedMemberId;

	public Rq(HttpServletRequest req) { // loginedMemberId 검증
		
		HttpSession httpSession = req.getSession();
		
		int loginedMemberId = 0;
		
		if(httpSession.getAttribute("loginedMemberId") != null) { // 로그인 체크
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId"); // 가져와서 쓰려면 형변환
		}
		
		this.loginedMemberId = loginedMemberId;
	}
	
}
