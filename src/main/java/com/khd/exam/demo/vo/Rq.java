package com.khd.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.khd.exam.demo.util.Utility;

import lombok.Getter;

public class Rq {
	@Getter
	private int loginedMemberId;
	private HttpServletRequest req;
	private HttpServletResponse resp;

	public Rq(HttpServletRequest req, HttpServletResponse resp) { // loginedMemberId 검증
		this.req = req;
		this.resp = resp;
		
		HttpSession httpSession = req.getSession();
		
		int loginedMemberId = 0;
		
		if(httpSession.getAttribute("loginedMemberId") != null) { // 로그인 체크
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId"); // 가져와서 쓰려면 형변환
		}
		
		this.loginedMemberId = loginedMemberId;
	}

	public void jsPrintHistoryBack(String msg) throws IOException {
		resp.setContentType("text/html; charset=UTF-8;");
		
		println("<script>");
		
		if(!Utility.empty(msg)) { // 로그인이 안되어있다면 출력
			println("alert('" + msg + "')");
		}
		
		println("history.back();");
		println("</script>");
		
	}
	
	private void print(String str) {
		try {
			resp.getWriter().append(str);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void println(String str) {
		print(str + "\n");
		
	}
	
}
