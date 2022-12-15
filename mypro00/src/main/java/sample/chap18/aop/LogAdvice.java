package sample.chap18.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Component
@Log4j
public class LogAdvice {
	
//	@Before("execution(* sample.chap18.service.SampleService*.*(..))")
//	public void logBefore() {
//		log.info("===================================");
//		
//	}
	
	@Before("execution(* sample.chap18.service.SampleServiceImpl.doAdd(String, String))")
	public void logBefore1() {
		log.info("===================================");
		
	}
	
//	 //*: 접근제한자                                            //..은  모든 타입과 모든 매개변수 개수
//	@Before("execution(* sample.chap18.service.SampleService*.*(..))")
//	//@After("execution(* sample.chap18.service.SampleService*.*(..))")
//	//@Around("execution(* sample.chap18.service.SampleService*.*(..))")
//	public void logBefore() {
//		System.out.println("LogAdvice.logBefore 실행:::::::::");
//		log.info("===================================");
//	}
	
	
////	@Before("execution(* sample.chap18.service.SampleService*.*(String, String)) && args(paramStr1, paramStr2)")
//	@After("execution(* sample.chap18.service.SampleService*.*(String, String)) && args(paramStr1, paramStr2)")
//	public void logBeforeWithParam(String paramStr1, String paramStr2) {
//	
//		System.out.println("LogAdvice.logBefore 실행:::::::::");
//	
//		log.info("paramStr1: " + paramStr1);
//		log.info("paramStr2: " + paramStr2);
//	
//	}
	
	//메서드 내에서 발생된 예외를 사용하길 원하는 경우, 
	//@AfterThrowing 어노테이션에 throwing 속성을 이용하여 예외를 받을 변수를 지정하고,
	//메서드이 매개변수의 이름을 동리하게 설정하면, 메서드 내에서 발생된 예외의 메시지를 처리할 수 있습니다.  
	@AfterThrowing(pointcut = "execution(* sample.chap18.service.SampleService*.*(..) ) " , 
		           throwing = "exception1" )
	public void logException(Exception exception1) {
		System.out.println("LogAdvice.logException 실행:::::::::");
		log.info("예외: " + exception1);
	
	}

	
	@Around("execution(* sample.chap18.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
	
		log.info("SampleServiceImpl.대상메소드 실행 전, logTime 메소드의 나머지 처음 부분 실행 시작");
		long start = System.currentTimeMillis();
	
		log.info("System.currentTimeMillis(): " + start);
	
		log.info("Target: " + pjp.getTarget());
		log.info("Param: " + Arrays.toString(pjp.getArgs()));
	
		Object result = null;
	
		log.info("SampleServiceImpl.대상메소드 실행 전, logTime 메소드의 나머지 처음 부분 실행 시작 완료");
	
		try {
				log.info("SampleServiceImpl.대상메소드 실행 시작");
				//invoke Target method 
				result = pjp.proceed();  //target을 실행.SampleServiceImpl.대상메소드가 실행
		} catch (Throwable e) {
			e.printStackTrace();
		}
	
		log.info("SampleServiceImpl.대상메소드 실행 완료 후, logTime 메소드의 나머지 실행 시작");
		long end = System.currentTimeMillis();
		log.info("TIME: "  + (end - start));
	
		return result;
	}
	
	
//	@Before("execution(* sample.chap18.service.Sample*.do*(String, String))")
//	public void logBefore1() {
//		System.out.println("LogAdvice.logBefore 실행:::::::::");
//		log.info("===================================");
//	
//	}
	
//	@Before("execution(* sample.chap18.service.Sample*.doAdd*(..))")
//	public void logBefore2() {
//	
//		System.out.println("LogAdvice.logBefore 실행:::::::::");
//	
//		log.info("===================================");
//	
//	}

}
