package com.khd.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khd.exam.demo.Utlity.Utility;
import com.khd.exam.demo.service.MemberService;
import com.khd.exam.demo.vo.Member;

@Controller
public class UsMemberController {
	
	private MemberService memberService;
	// 의존성 주입 - 객체만들지 않아도 됨
	@Autowired 
	public UsMemberController(MemberService memberService){
		this.memberService = memberService;
	}
	
// 액션 메서드
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody 
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if(Utility.empty(loginId)) { // 유효성 검사(공백)
			return "아이디를 입력해주세요.";
		}
		if(Utility.empty(loginPw)) { 
			return "비밀번호를 입력해주세요.";
		}
		if(Utility.empty(name)) { 
			return "이름을 입력해주세요.";
		}
		if(Utility.empty(nickname)) { 
			return "닉네임을 입력해주세요.";
		}
		if(Utility.empty(cellphoneNum)) { 
			return "핸드폰 번호를 입력해주세요.";
		}
		if(Utility.empty(email)) { 
			return "이메일을 입력해주세요.";
		}
		
		
		int id = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if(id == -1) { // memberService에서 중복 로그인 아이디가 걸렸을시
			return Utility.f("이미 사용중인 아이디(%s) 입니다.", loginId);
		}
		
		if(id == -2) { // 이름 + 이메일 중복체크
			return Utility.f("이미 사용중인 이름(%s)과 이메일(%s) 입니다.", name, email);
		}
		
		Member member = memberService.getMemberById(id);
		
		return member;
	}
	
	
}
