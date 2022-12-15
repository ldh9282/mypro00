package com.spring.mypro00.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MyMemberVO {

	private String userid ;
	private String userpw ;
	private String userName ;
	private Timestamp mregDate ;
	private Timestamp mmodDate ;
	private String mdropFlg ;
	private boolean enabled ;
	
	private List<MyAuthorityVO> authList ;
}
