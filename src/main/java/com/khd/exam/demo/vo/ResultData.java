package com.khd.exam.demo.vo;

import java.util.Map;

import com.khd.exam.demo.util.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultData<DT> { // <~~> == 제너릭

	// S-1, F-1, F-2 ...성공, 실패 여부
	private String resultCode;
	private String msg;
	private DT data1; // <DT> ~ 데이터 타입으로 쓰겠다고 지정. 내부가 아닌 외부에서 data1을 정함
	private String data1Name;
	private String data2Name;
	private Object data2;
	private Map<String, Object> body;
	
	public ResultData(String resultCode, String msg, Object... args) {
		this.resultCode = resultCode;
		this.msg = msg;
		this.body = Utility.mapOf(args);
	}
	
	// 오버로딩 // from 리턴타입 메서드로 만든것 뿐
	public static <DT> ResultData <DT> from(String resultCode, String msg) {
		
		return from(resultCode, msg, null, null); 
	}
	// 오버로딩 // from 리턴타입 메서드로 만든것 뿐
	public static <DT> ResultData <DT> from(String resultCode, String msg, String data1Name, DT data1) {
		ResultData<DT> rd = new ResultData<>(); // ArrayList<>() 와 동일함. <>에 넣지 않아도 됨
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1Name = data1Name;
		rd.data1 = data1;
		
		return rd;
	}
	
	public boolean isSuccess() { // 성공 true
		return this.resultCode.startsWith("S-");
	}
	
	public boolean isFail() { // 실패시 false
		return isSuccess() == false;
	}
	public void setData2(String data2Name, Object data2) {
		this.data2Name = data2Name;
		this.data2 = data2;
		
	}
	
}
