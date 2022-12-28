package com.spring.mypro00.repository_dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.mypro00.common.paging.MyBoardPagingDTO;
import com.spring.mypro00.domain.MyBoardVO;

@Repository
public class MyBoardDAO {
	
	private SqlSession sqlSession ;
	
	//생성자를 통한 주입
	public MyBoardDAO(SqlSession sqlSession) {
		System.out.println("DAO 생성자를 이용한 주입");
		this.sqlSession = sqlSession ;
	}
	
	//Setter를 이용한 주입
//	@Autowired
//	public void setSqlSession(SqlSession sqlSession) {
//		System.out.println("Setter를 이용한 주입");
//		this.sqlSession = sqlSession ;
//	}
	
	//게시물 목록 - 데이터 조회
	public List<MyBoardVO> selectDAOMyBoardList(MyBoardPagingDTO myBoardPagingDTO){
		
		return sqlSession.selectList("MyBoardDAOMapper.selectDAOMyBoardList", myBoardPagingDTO) ;
	}
	
	//게시물 총 수 조회
	public long selectRowAmountTotal(MyBoardPagingDTO myBoardPagingDTO) {
		
		return sqlSession.selectOne("MyBoardDAOMapper.selectDAORowAmtTotal", myBoardPagingDTO) ;
	}
	
	

}
