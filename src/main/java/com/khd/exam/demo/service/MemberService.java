package com.khd.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khd.exam.demo.repository.MemberRepository;

@Service
public class MemberService {
	
	private MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String eamil) {
		
	}

}
