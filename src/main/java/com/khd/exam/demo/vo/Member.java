package com.khd.exam.demo.vo;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 생성자 만듬
@AllArgsConstructor // 인자 포함한 생성자를 만듬
public class Member{
	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	private String loginPw;
	private String authLevel;
	private String name;
	private String nickname;
	private String cellphoneNum;
	private String email;
	private boolean delStatus;
	private String delDate;
	
	public String delStatusStr() {
		if(delStatus == false) {
			return "미삭제";
		}
		return "삭제";
	}

	public String delDateStr() {
		if(delDate == null) {
			return "없음";
		}
		return delDate.substring(2, 16);
	}


}

