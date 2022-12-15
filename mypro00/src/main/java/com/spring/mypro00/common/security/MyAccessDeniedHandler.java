package com.spring.mypro00.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
//@Component("myAccessDeniedHandler") 어노테이션을 이용해서 빈을 생성하면 않됩니다.
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		log.info("accessDeniedException: " + accessDeniedException);
		response.sendRedirect("/mypro00/accessForbiddenError");
		
	}
	
	

}
