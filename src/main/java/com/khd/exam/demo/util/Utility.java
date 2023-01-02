package com.khd.exam.demo.util;

import java.text.SimpleDateFormat;

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
						// 첫번째 매개인자  두번째 매개인자
	public static String f(String format, Object...args) {
		
		return String.format(format, args);
	}
	
	public static String jsHistoryBack(String msg) {
		
		if(msg == null) {
			msg = "";
		}
		
		return Utility.f("""
					<script>
						const msg = '%s'.trim();
						if(msg.length > 0){
							alert(msg)
						}
						history.back();
					</script>
				""", msg);
	}
	
	public static String jsReplace(String msg, String uri) {
		if(msg == null) {
			msg = "";
		}
		if(uri == null) {
			uri = "";
		}
		
		return Utility.f("""
				<script>
					const msg = '%s'.trim();
					if(msg.length > 0){
						alert(msg)
					}
					location.replace('%s');
				</script>
			""", msg, uri);
	}
	
	public static String getDateStrLater(long seconds) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = format.format(System.currentTimeMillis() + seconds * 1000);

		return dateStr;
	}

	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}

		return sb.toString();
	}
	public static String jsReturnOnView(String string, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
