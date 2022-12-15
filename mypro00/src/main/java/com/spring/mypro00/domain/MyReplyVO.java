package com.spring.mypro00.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyReplyVO {
	private long rno ;
	private long bno ;
	private String rcontent ;
	private String rwriter ;
	private Timestamp rregDate ;
	private Timestamp rmodDate ;
	private long prno ;
	private int lvl ;
	
	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	
	

}
