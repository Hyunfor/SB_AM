package com.khd.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khd.exam.demo.service.MemberService;

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
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String eamil) {
		
		return "가입!";
	}
	
	
}
