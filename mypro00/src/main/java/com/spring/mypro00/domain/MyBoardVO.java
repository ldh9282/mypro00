package com.spring.mypro00.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class MyBoardVO {
	
	private long bno ;
	private String btitle ;
	private String bcontent ;
	private String bwriter ;
	private Date bregDate ;
	private Timestamp bmodDate ;
	private int bviewCnt ;
	private int breplyCnt ;
	private int bdelFlag ;   //1: 삭제요청됨, 0: 유지
	
	private List<MyBoardAttachFileVO> attachFileList ;

	//마이바티스의 여러 오류를 해결하기 위해 반드시 추가합니다.
	public MyBoardVO() {
		System.out.println("MyBoardVO의 기본생성자입니다.");
	}
	
	public MyBoardVO(long bno, String btitle, String bcontent, String bwriter, int bviewCnt, int breplyCnt,
			int bdelFlag, Date bregDate, Timestamp bmodDate) {
		this.bno = bno;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.bwriter = bwriter;
		this.bviewCnt = bviewCnt;
		this.breplyCnt = breplyCnt;
		this.bdelFlag = bdelFlag;
		this.bregDate = bregDate;
		this.bmodDate = bmodDate;
		System.out.println("MyBoardVO의 All Arguement 생성자입니다.");
	}

	
	public void setBno(long bno) {
		this.bno = bno;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	
	public void setBwriter(String bwriter) {
		this.bwriter = bwriter ;
	}

	public void setAttachFileList(List<MyBoardAttachFileVO> attachFileList) {
		this.attachFileList = attachFileList;
	}
	
	





	
	

}
