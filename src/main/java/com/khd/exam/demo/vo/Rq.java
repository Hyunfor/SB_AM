package com.khd.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.khd.exam.demo.service.MemberService;
import com.khd.exam.demo.util.Utility;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) { // loginedMemberId 검증
		this.req = req;
		this.resp = resp;
		
		this.session= req.getSession();
		
		int loginedMemberId = 0;
		Member loginedMember = null;
		
		if(session.getAttribute("loginedMemberId") != null) { // 로그인 체크
			loginedMemberId = (int) session.getAttribute("loginedMemberId"); // 가져와서 쓰려면 형변환
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		
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
	
	public String getProfileImgUri(int membeId) {
		return "/common/genFile/file/member/" + membeId + "/extra/profileImg/1";
	}

	public String getProfileFallbackImgUri() {
		return "https://via.placeholder.com/150/?text=*^_^*";
	}

	public String getProfileFallbackImgOnErrorHtml() {
		return "this.src = '" + getProfileFallbackImgUri() + "'";
	}

	public String getRemoveProfileImgIfNotExitOnErrorHtmlAttr() {
		return "$(this).remove()";
	}
	
}
