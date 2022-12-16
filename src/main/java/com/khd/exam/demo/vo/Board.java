package com.khd.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 생성자 만듬
@AllArgsConstructor // 인자 포함한 생성자를 만듬
public class Board{
	private int id;
	private String regDate;
	private String updateDate;
	private String code;
	private String name;
	private String delStatus;
	private String delDate;
	
	
}
