package com.khd.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khd.exam.demo.service.MemberService;
import com.khd.exam.demo.util.Utility;
import com.khd.exam.demo.vo.Member;
import com.khd.exam.demo.vo.ResultData;
import com.khd.exam.demo.vo.Rq;

@Controller
public class UsMemberController {
	
	private MemberService memberService;
	private Rq rq;

	// 의존성 주입 - 객체만들지 않아도 됨
	@Autowired 
	public UsMemberController(MemberService memberService, Rq rq){
		this.memberService = memberService;
		this.rq = rq;
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
		
		return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg(), "member", member);
		
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() { // 로그인 요청시 로그인 페이지를 보이게 jsp에 요청하는 역할		
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody // 응답하는 화면 보여주는 역할
	public String doLogin(String loginId, String loginPw) {
		
		if(Utility.empty(loginId)) { // 유효성 검사(공백)
			return Utility.jsHistoryBack("아이디를 입력해주세요.");
		}
		if(Utility.empty(loginPw)) { 
			return Utility.jsHistoryBack("비밀번호를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) { // 아이디가 없을시
			return Utility.jsHistoryBack("존재하지 않는 아이디 입니다.");
		}
		if(member.getLoginPw().equals(loginPw) == false) { // 비밀번호가 일치하지 않을 시
			return Utility.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}

		rq.login(member);

		return Utility.jsReplace(Utility.f("%s님 환영합니다.", member.getNickname()),"/"); // 로그인 시 main으로
		
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody 
	public String doLogout() {

		rq.logout();
		
		return Utility.jsReplace("로그아웃 되었습니다.", "/");
		
	}
	
	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {
		return "usr/member/myPage";
	}

	@RequestMapping("/usr/member/checkPassword")
	public String showCheckPassword() {
		return "usr/member/checkPassword";
	}

	@RequestMapping("/usr/member/doCheckPassword")
	public String doCheckPassword(String loginPw) {

		if (Utility.empty(loginPw)) {
			return rq.jsReturnOnView("비밀번호를 입력해주세요", true);
		}

		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return rq.jsReturnOnView("비밀번호가 일치하지 않습니다", true);
		}

		return "usr/member/modify";
	}

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String nickname, String cellphoneNum, String email) {

		if (Utility.empty(nickname)) {
			return Utility.jsHistoryBack("닉네임을 입력해주세요");
		}
		if (Utility.empty(cellphoneNum)) {
			return Utility.jsHistoryBack("전화번호를 입력해주세요");
		}
		if (Utility.empty(email)) {
			return Utility.jsHistoryBack("이메일을 입력해주세요");
		}

		memberService.doModify(rq.getLoginedMemberId(), nickname, cellphoneNum, email);

		return Utility.jsReplace("회원정보가 수정되었습니다", "/");
	}

	@RequestMapping("/usr/member/passWordModify")
	public String passWordModify() {
		return "usr/member/passWordModify";
	}

	@RequestMapping("/usr/member/doPassWordModify")
	@ResponseBody
	public String doPassWordModify(String loginPw, String loginPwConfirm) {

		if (Utility.empty(loginPw)) {
			return Utility.jsHistoryBack("새 비밀번호를 입력해주세요");
		}
		if (Utility.empty(loginPwConfirm)) {
			return Utility.jsHistoryBack("새 비밀번호 확인을 입력해주세요");
		}
		if (loginPw.equals(loginPwConfirm) == false) {
			return Utility.jsHistoryBack("비밀번호가 일치하지 않습니다");
		}

		memberService.doPassWordModify(rq.getLoginedMemberId(), loginPw);

		return Utility.jsReplace("비밀번호가 수정되었습니다", "/");
	}
	
}
