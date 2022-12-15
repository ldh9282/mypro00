package com.spring.mypro00.common.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class MyLoginLogoutController {
	//아래의 2개 메서드는 각각 로그인 페이지, 로그아웃 페이지를 브라우저에 표시해주는 메서드입니다.
	//로그인 처리나 로그아웃 처리는 스프링 시큐리티가 수행합니다.
	
	
	
	//로그인 페이지 호출 메서드
	//로그인 페이지 호출: 로그인 JSP 페이지 이름 문자열 반환: WEB-INF/views/common/myLogin.jsp
	//로그인 페이지 호출은 GET 방식으로만 제한됨
	
	//스프링 시큐리티가 반환하는 로그인 처리 결과에 대하여 메시지 처리를 하려면,
	//Strirng 유형의 error, logout 매개변수가 선언되어야 함
	//@GetMapping("/myLogin")
	@GetMapping("/login")
	public String loginPageGET(String error, String logout, Model model) {
		
		if (error != null) {
			log.info("로그인 오류 시 error.hashCode(): " + error.hashCode());
			model.addAttribute("error", "로그인 오류. 계정이나 암호를 확인하세요.") ;
			
		} else if (logout != null){
			log.info("로그 아웃 후에 호출된 로그인 페이지의 경우, error.hashCode(): " + logout.hashCode());
			model.addAttribute("logout", "로그 아웃 후에 호출된 로그인 페이지 입니다.") ;
			
		} else {
		
			//정상적인 로그인 페이지 호출
			model.addAttribute("normal", "직접 로그인 페이지를 호출해서 정상적으로 표시된 경우입니다.") ;
		}
		
		return "common/myLogin" ;
		
	}
	
	//로그아웃 페이지 호출 메서드: 로그아웃 JSP 페이지 이름 문자열 반환: WEB-INF/views/common/myLogout.jsp
	//로그아웃 페이지 호출은 GET 방식으로만 제한됨
	//@GetMapping("/myLogout")
	@GetMapping("/logout")
	public String logoutPageGET() {
		
		log.info("로그 아웃 페이지 호출");
		
		return "common/myLogout" ;
	}
	

}
