package com.spring.mypro00.test02_mapper;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spring.mypro00.domain.MyMemberVO;
import com.spring.mypro00.mapper.MyMemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration      //폴더 구분자로 \\ 사용 시 오류 발생. /를 사용하세요.
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml" ,
                       "file:src/main/webapp/WEB-INF/spring/security-context.xml" ,
                       "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class MyMemberMapperTests {

    //사용자 패스워드 암호화 
    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder pwencoder;
    
    @Setter(onMethod_ = @Autowired)
    private MyMemberMapper myMemberMapper;

    //회원 등록 테스트: 테스트(1)
//    @Test
//    public void testInsertMyMember() {
//    	
//        MyMemberVO myMember = new MyMemberVO();
//        
//        for(int i = 0; i < 100; i++) {
//
//            myMember.setUserpw(pwencoder.encode("pw" + i));
//
//            if(i < 80) {
//                myMember.setUserid("user" + i);
//                myMember.setUserName("일반사용자" + i);
//            
//            } else if (i < 90) {
//                myMember.setUserid("manager" + i);
//                myMember.setUserName("운영자" + i);
//                
//            } else {
//                myMember.setUserid("admin" + i);
//                myMember.setUserName("관리자" + i);
//            }
//            
//            log.info(myMember);            
//            myMemberMapper.insertMyMember(myMember);
//        } //for-End
//    }
    
//    //회원 권한 등록 테스트: 테스트(2)
//    @Test
//    public void testInsertMyMemAuthority() {
//		
//        MyAuthorityVO myAuthority = new MyAuthorityVO();
//        
//        for(int i = 0; i < 100; i++) {
//
//            if(i < 80) {
//                myAuthority.setUserid("user" + i);
//                myAuthority.setAuthority("ROLE_USER");
//                
//            } else if (i < 90) {
//                myAuthority.setUserid("manager" + i);
//                myAuthority.setAuthority("ROLE_MANAGER");
//                
//            } else {
//                myAuthority.setUserid("admin" + i);
//                myAuthority.setAuthority("ROLE_ADMIN");
//                
//            }
//            log.info(myAuthority);
//            myMemberMapper.insertMyMemberAuth(myAuthority);
//        } //for-End
//        
//        myAuthority.setUserid("admin99");
//        myAuthority.setAuthority("ROLE_MANAGER");
//        myMemberMapper.insertMyMemberAuth(myAuthority);
//        
//        myAuthority.setUserid("admin99");
//        myAuthority.setAuthority("ROLE_USER");
//        myMemberMapper.insertMyMemberAuth(myAuthority);
//        
//        myAuthority.setUserid("admin91");
//        myAuthority.setAuthority("ROLE_MANAGER");
//        myMemberMapper.insertMyMemberAuth(myAuthority);
//    }
//    
    //회원 정보 조회 테스트: 테스트(3) 
    @Test
    public void testRead() {
        MyMemberVO myMember = myMemberMapper.selectMyMember("admin99");
        log.info(myMember);
        
        myMember.getAuthList().forEach(authorityVO -> log.info(authorityVO));
    }

} //class-end

