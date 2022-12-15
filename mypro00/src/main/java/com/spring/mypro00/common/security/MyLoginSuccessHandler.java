package com.spring.mypro00.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

//로그인이 성공된 후에 처리할 내용이 구현된 클래스
//AuthenticationSuccessHandler 인터페이스의 구현객체로 생성.
@Log4j
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) //로그인 성공 시 생성되는 인증객체 
												throws IOException, ServletException {
		
		log.info("로그인 성공 후: MyLoginSuccessHandler.onAuthenticationSuccess() 시작......");
		log.info("전달된 Authentication 객체 정보: " + authentication);
		
		//권한(Authority, 롤) 목록을 저장하는 객체 생성
		List<String> authList = new ArrayList<String>() ;
		
		//권한이름 추출 --> List에 저장
		authentication.getAuthorities()
					  .forEach( 
							  authority -> {
								  				authList.add(authority.getAuthority()) ;
					                        } 
					   );
		
		log.info("권한 목록: " + authList); //권한(롤)이름 리스트를 콘솔 표시
		
		if (authList.contains("ROLE_ADMIN")) {
			response.sendRedirect("/mypro00/myboard/detail?bno=1");
			
		} else {
			response.sendRedirect("/mypro00/");
		}
		
	}

}
