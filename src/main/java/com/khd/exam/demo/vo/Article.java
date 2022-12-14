package com.khd.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 생성자 만듬
@AllArgsConstructor // 인자 포함한 생성자를 만듬
public class Article{
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String title;
	private String body;
	private int viewCount;
	
	private String writerName;
	private boolean actorCanChangeData;
	private int sumReactionPoint;
	private int goodReactionPoint;
	private int badReactionPoint;
	
	public String getForPrintBody() {
		return this.body.replaceAll("\n", "<br> ");
	}
	
}

