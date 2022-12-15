package sample.chap18_aoptest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import sample.chap18.service.SampleService;

@WebAppConfiguration    //테스트 시, DispatcherServlet의 servlet-context.xml 설정 구성파일(들)을 사용하기 위한 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
                       "file:src/main/webapp/WEB-INF/spring/mybatis-context.xml"})
@Log4j
public class SampleServiceAopTests {
	@Setter(onMethod_ = { @Autowired})
	private SampleService sampleService ;
	
	@Test
	public void testAopClass() {
		log.info("sampleServie: " + sampleService);
		log.info(sampleService.getClass().getName());
	}
	
	@Test
	public void testAdd() throws Exception{
		log.info(sampleService.doAdd("123", "456"));
	}

}
