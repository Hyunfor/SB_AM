package com.khd.exam.demo.vo;

import lombok.Getter;

public class ResultData {

	// S-1, F-1, F-2 ...성공, 실패 여부
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private Object data1;
	
	// 오버로딩 // from 리턴타입 메서드로 만든것 뿐
	public static ResultData from(String resultCode, String msg) {
		
		return from(resultCode, msg, null); 
	}
	// 오버로딩 // from 리턴타입 메서드로 만든것 뿐
	public static ResultData from(String resultCode, String msg, Object data1) {
		ResultData rd = new ResultData();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1 = data1;
		
		return rd;
	}
	
	public boolean isSuccess() { // 성공 true
		return this.resultCode.startsWith("S-");
	}
	
	public boolean isFail() { // 실패시 false
		return isSuccess() == false;
	}
	
}
