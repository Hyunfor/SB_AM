package com.khd.exam.demo.Utlity;

public class Utility {

	public static boolean empty(String obj) {
		
		if(obj == null) { // 중복되는 유효성 검사
			return true;
		}
		
//		if(obj instanceof String == false) { // obj 타입이 String인지 물어보는 instanceof 연산자
//			return true;
//		}
		
		String str = (String) obj; // obj를 str로 형변환 후
		
		return str.trim().length() == 0;
	}
	
	

}
