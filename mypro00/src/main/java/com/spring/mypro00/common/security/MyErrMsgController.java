package com.spring.mypro00.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

//접근금지(403) 오류 발생 시, 스프링 시큐리티의 요청을 처리할 컨트롤러
@Controller
@Log4j
public class MyErrMsgController {
	
	@GetMapping("/accessForbiddenError")
	public String callAccessForbiddenPage(Authentication authentication, Model model) {
		log.info("==============================================================") ;
		log.info("authentication: " + authentication) ;
		log.info("==============================================================") ;
		model.addAttribute("msg", "접근이 금지됨") ;
		
		return "common/err_msg/myAccessForbiddenMsg" ;
	}

}
