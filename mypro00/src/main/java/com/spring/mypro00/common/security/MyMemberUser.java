package com.spring.mypro00.common.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.spring.mypro00.domain.MyMemberVO;

public class MyMemberUser extends User {
	
	private static final long serialVersionUID = 10L;
	
	private MyMemberVO myMember ;

	public MyMemberUser(MyMemberVO myMember) {
		//super(String username, String password, List<GrantedAuthority>authorities);
		super(myMember.getUserid(),
			  myMember.getUserpw(),
			  myMember.getAuthList()
			          .stream()  //List<MyAuthorityVO> --> Stream<MyAuthorityVO>
			          .map(auth -> new SimpleGrantedAuthority(auth.getAuthority())) //Stream<GrantedAuthority> 변환됨 각 권한을 부여된 권한으로 변환
			          .collect(Collectors.toList())  //GrantedAuthority객체들로 변경된 List 객체가 생성됨, List<GrantedAuthority>로 변환됨
		);
		
		System.out.println("MyUser생성자에 전달된 MyMemberVO정보:" + myMember.toString());
		System.out.println("MyUser 객체 생성을 통해 MyUser의 부모객체(User객체) 생성됨");
		System.out.println("=====================================================");
		
		this.myMember = myMember ;
	}
	
}
