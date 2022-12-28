package com.spring.mypro00.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mypro00.common.paging.MyBoardPagingDTO;
import com.spring.mypro00.domain.MyBoardVO;
import com.spring.mypro00.repository_dao.MyBoardDAO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class MyBoardDAOService {
	
	@Setter(onMethod_ = @Autowired)
	private MyBoardDAO myBoardDAO ;
	
	//게시물 목록 조회 서비스
	public List<MyBoardVO> getBoardListDAO (MyBoardPagingDTO myBoardPagingDTO) {
		return  myBoardDAO.selectDAOMyBoardList(myBoardPagingDTO);
	}
	
	//게시물 총수 조회 서비스
	public long getRowAmtTotalDAO (MyBoardPagingDTO myBoardPagingDTO) {
		return myBoardDAO.selectRowAmountTotal(myBoardPagingDTO) ;
	}
	


}
