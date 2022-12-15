package com.spring.mypro00.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring.mypro00.domain.MyMemberVO;
import com.spring.mypro00.mapper.MyMemberMapper;

import lombok.extern.log4j.Log4j;

@Log4j
public class MyMemberDetailsService implements UserDetailsService{
	
	//setter 방식으로 의존성 주입이 구현되어야 security-context.xml에서 오류가 발생되지 않음
	private MyMemberMapper myMemberMapper ;
	
	@Autowired
	public void setMyMemberMapper(MyMemberMapper myMemberMapper) {
		this.myMemberMapper = myMemberMapper ;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//우리의 userid 값을 스프링 시큐리티는 username 이름의 매개변수로 사용합니다.
		log.info("MyMemberDetailsService의 loadUserByUsername에 전달된 username: " + username);
		
		MyMemberVO myMember = myMemberMapper.selectMyMember(username);
		
		//MyMemberUser 객체 생성 -> UserDetails 유형의 User 객체로 변환되어 반환됨
		return myMember == null ? null : new MyMemberUser(myMember);
	}

}
