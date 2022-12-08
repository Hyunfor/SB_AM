package com.khd.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khd.exam.demo.service.MemberService;
import com.khd.exam.demo.util.Utility;
import com.khd.exam.demo.vo.Member;
import com.khd.exam.demo.vo.ResultData;

import jakarta.servlet.http.HttpSession;

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
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		if(Utility.empty(loginId)) { // 유효성 검사(공백)
			return ResultData.from("F-1", "아이디를 입력해주세요.");
		}
		if(Utility.empty(loginPw)) { 
			return ResultData.from("F-2", "비밀번호를 입력해주세요.");
		}
		if(Utility.empty(name)) { 
			return ResultData.from("F-3", "이름을 입력해주세요.");
		}
		if(Utility.empty(nickname)) { 
			return ResultData.from("F-4", "닉네임을 입력해주세요.");
		}
		if(Utility.empty(cellphoneNum)) { 
			return ResultData.from("F-5", "핸드폰 번호를 입력해주세요.");
		}
		if(Utility.empty(email)) { 
			return ResultData.from("F-6", "이메일을 입력해주세요.");
		}
		
		
		ResultData<Integer> doJoinRd = memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if(doJoinRd.isFail()) { // memberService에서 중복 아이디 or 이름, 이메일 체크	
			return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg()); // 방법1
//			return (ResultData) doJoinRd; // 방법2
		}
	
		Member member = memberService.getMemberById((int)doJoinRd.getData1()); // Data는 Object라 int로 형변환
		
		return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg(), member);
		
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody 
	public ResultData doLogin(HttpSession httpSession, String loginId, String loginPw) {
		
		if(httpSession.getAttribute("loginMemberId") != null) { // 중복 로그인 방지
			return ResultData.from("F-1", "이미 로그인 되어있습니다.");
		}
		
		if(Utility.empty(loginId)) { // 유효성 검사(공백)
			return ResultData.from("F-2", "아이디를 입력해주세요.");
		}
		if(Utility.empty(loginPw)) { 
			return ResultData.from("F-3", "비밀번호를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) { // 아이디가 없을시
			return ResultData.from("F-4", "존재하지 않는 아이디 입니다.");
		}
		if(member.getLoginPw().equals(loginPw) == false) { // 비밀번호가 일치하지 않을 시
			return ResultData.from("F-5", "비밀번호가 일치하지 않습니다.");
		}

		// 세션에 회원번호 저장 . article의 번호와 일치하는 회원번호를 가져오기 위함
		httpSession.setAttribute("loginedMemberId", member.getId()); 

		return ResultData.from("S-1", Utility.f("%s님 환영합니다.", member.getNickname()));
		
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody 
	public ResultData doLogout(HttpSession httpSession) {
		
		if(httpSession.getAttribute("loginMemberId") != null) { // 중복 로그인 방지
			return ResultData.from("F-1", "로그아웃 상태입니다.");
		}
		
		httpSession.removeAttribute("loginedMemberId"); // 세션에 저장된 회원번호를 삭제

		return ResultData.from("S-1", "로그아웃 되었습니다.");
		
	}
	
	
}
