package com.khd.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository { // class는 사용 불가능 100% 추상메서드인 interface로 바꿔야 사용가능 , 모든 구현부 제거

	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String eamil);
		

}

