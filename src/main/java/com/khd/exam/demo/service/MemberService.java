package com.khd.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khd.exam.demo.repository.MemberRepository;
import com.khd.exam.demo.vo.Member;

@Service
public class MemberService {
	
	private MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		
		// 중복 로그인 아이디 체크
		Member existsMember = getMemberByLoginId(loginId);
		
		if(existsMember != null) { 
			return -1;
		}
		
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
	
	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

}
