package com.khd.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khd.exam.demo.service.MemberService;
import com.khd.exam.demo.util.Utility;
import com.khd.exam.demo.vo.Member;
import com.khd.exam.demo.vo.Rq;

@Controller
public class AdmMemberController {

	private MemberService memberService;
	private Rq rq;

	@Autowired
	public AdmMemberController(MemberService memberService, Rq rq) {
		this.memberService = memberService;
		this.rq = rq;
	}

	@RequestMapping("/adm/member/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") String authLevel,
			@RequestParam(defaultValue = "loginId,name,nickname") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		int membersCount = memberService.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword) - 1;
		
		if (page <= 0) {
			return rq.jsReturnOnView("페이지번호가 올바르지 않습니다", true);
		}

		int itemsInAPage = 10;
		int pagesCount = (int) Math.ceil((double) membersCount / itemsInAPage);

		List<Member> members = memberService.getMembers(authLevel, searchKeywordTypeCode, searchKeyword, itemsInAPage,
				page);

		model.addAttribute("authLevel", authLevel);
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("page", page);
		model.addAttribute("members", members);
		model.addAttribute("membersCount", membersCount);
		model.addAttribute("pagesCount", pagesCount);

		return "adm/member/list";
	}
	
	// 관리자 권한으로 회원 선택 삭제기능
	@RequestMapping("/adm/member/doDeleteMembers")
	@ResponseBody
	public String doDeleteMembers(@RequestParam(defaultValue = "") String ids) {

		if (Utility.empty(ids)) {
			return Utility.jsHistoryBack("선택한 회원이 없습니다");
		}

		if (ids.equals("1")) {
			return Utility.jsHistoryBack("관리자 계정은 삭제할 수 없습니다");
		}

		List<Integer> memberIds = new ArrayList<>();

		for (String idStr : ids.split(",")) {
			memberIds.add(Integer.parseInt(idStr));
		}

		memberService.deleteMembers(memberIds);

		return Utility.jsReplace("선택한 회원이 삭제되었습니다", "list");
	}
	
}