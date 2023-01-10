package com.khd.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 생성자 만듬
@AllArgsConstructor // 인자 포함한 생성자를 만듬
public class Reply {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String relTypeCode;
	private int relId;
	private String body;
	
	private String writerName;
	private boolean actorCanChangeData;
	
	private String reparent;
    private String redepth;
    private Integer reorder;
	
	public String getForPrintBody() {
		return this.body.replaceAll("\n", "<br> ");
	}
	
    public String getReparent() {
        return reparent;
    }
   
    public void setReparent(String reparent) {
        this.reparent = reparent;
    }

    public String getRedepth() {
        return redepth;
    }

    public void setRedepth(String redepth) {
        this.redepth = redepth;
    }

    public Integer getReorder() {
        return reorder;
    }

    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

}
