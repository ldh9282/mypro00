package com.spring.mypro00.common.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import lombok.extern.log4j.Log4j;

//로그인이 성공된 후에 처리할 내용이 구현된 클래스
//AuthenticationSuccessHandler 인터페이스의 구현객체로 생성.
@Log4j
public class MyLoginSuccessHandler2 extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) 
												throws ServletException, IOException {
		
		HttpSession session = request.getSession(false) ;
		
		if(session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		
		log.info("로그인 성공 후: MyLoginSuccessHandler.onAuthenticationSuccess() 시작......");
		log.info("전달된 Authentication 객체 정보: " + authentication);
		
		Set<String> authList = AuthorityUtils.authorityListToSet(authentication.getAuthorities()) ;
		log.info("ROLE NAMES: " + authList);
		
		//로그인을 요구한 요청 URL 정보를 가져 가져오는 코드 시작
		//RequestCache에 대하여 https://www.inflearn.com/questions/35556 페이지 설명 참고
		//로그인이 요청된 브라우저의 URL을 메모리에 저장
		RequestCache requestCache = new HttpSessionRequestCache() ;
		
		//메모리에 저장된 요청을 가져옴
		SavedRequest savedRequest = requestCache.getRequest(request, response) ;
		log.info("이전 요청(savedRequest): " + savedRequest);
		//[표시결과] 이전 요청(savedRequest): DefaultSavedRequest[http://localhost:8080/mypro00/myboard/register]
		
		if(savedRequest == null) {
			if (authList.contains("ROLE_ADMIN")) {
				response.sendRedirect("/mypro00/myboard/detail?bno=1");
				
			} else {
				response.sendRedirect("/mypro00/");
			}
			
		} else {
			super.onAuthenticationSuccess(request, response, authentication);			
		}

		
	}
	
	

	
		
	

}
