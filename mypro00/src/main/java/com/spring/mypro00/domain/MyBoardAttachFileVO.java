package com.spring.mypro00.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MyBoardAttachFileVO {
	
	private String uuid ;
	private String uploadPath ;
	private String fileName ;
	private String fileType ; //"I", "F"
	private long bno ;
	private String repoPath = "C:/myupload" ;
	
//	public void setBno(long bno) {
//		this.bno = bno ;
//	}

}
