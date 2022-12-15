package sample.chap04.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

//이 프로젝트의 모든 컨트롤러의 예외 발생 시, 이 클래스의 메서드로 처리하겠다는 선언
//@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		
		log.error("Exception........" + ex.getMessage());
		model.addAttribute("exception", ex) ;
		log.error(model);
		
		return "sample/error_page" ;
	}
	
	//해당URL요청을 처리하는 Controller 클래스의 메서드가 없을 때의
    //404오류
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException ex) {
        return "sample/custom404";
    }
	
	

}
