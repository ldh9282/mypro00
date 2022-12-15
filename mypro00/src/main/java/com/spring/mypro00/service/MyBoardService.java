package com.spring.mypro00.service;

import java.util.List;

import com.spring.mypro00.common.paging.MyBoardPagingDTO;
import com.spring.mypro00.domain.MyBoardAttachFileVO;
import com.spring.mypro00.domain.MyBoardVO;

public interface MyBoardService {

	//게시물 조회 - 목록
	//public List<MyBoardVO> getBoardList();
    public List<MyBoardVO> getBoardList(MyBoardPagingDTO myBoardPagingDTO);
    
    public long getRowAmountTotal(MyBoardPagingDTO myBoardPagingDTO) ;
	
    //게시물 등록 - selectKey 이용
    public Long registerBoard(MyBoardVO myBoard);
	
    //특정 게시물 조회(조회수 증가 고려)
    public MyBoardVO getBoard(long bno);
    
  //특정 게시물 조회 시, 게시물의 첨부파일 정보를 조회
    public List<MyBoardAttachFileVO> getAttachFiles(long bno) ;
    
    //게시물 조회: 게시물 조회 페이지 -> 게시물 수정 페이지 호출(by bno), 조회수 변화 없음
    //게시물 조회: 게시물 수정 후 -> 게시물 조회 페이지 호출(by bno), 조회수 증가 없음
    public MyBoardVO getBoardDetailModify(long bno) ;
    
    //특정 게시물 수정
    public boolean modifyBoard(MyBoardVO myBoard);
    
    //특정 게시물 삭제 - 실제 삭제 
    public boolean removeBoard(long bno);

    //특정 게시물 삭제 요청 - 해당 글의 bdelFlag을 1로 수정 
    public boolean setBoardDeleted(long bno);
	
    //게시물 삭제(관리자) – 사용자 삭제 요청된 게시물(bdelFlag = 1) 전체 삭제
    //public int removeAllDeletedBoard();


}
