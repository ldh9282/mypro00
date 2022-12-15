package com.spring.mypro00.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.mypro00.common.paging.MyBoardPagingDTO;
import com.spring.mypro00.domain.MyBoardAttachFileVO;
import com.spring.mypro00.domain.MyBoardVO;
import com.spring.mypro00.mapper.MyBoardAttachFileMapper;
import com.spring.mypro00.mapper.MyBoardMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MyBoardServiceImpl implements MyBoardService {
	
	//매퍼 인터페이스 주입: 생성자를 이용한 자동 주입
	private MyBoardMapper myBoardMapper ;
	
	private MyBoardAttachFileMapper myBoardAttachFileMapper ;
	
	public MyBoardServiceImpl(MyBoardMapper myBoardMapper, 
			                  MyBoardAttachFileMapper myBoardAttachFileMapper) {
		this.myBoardMapper = myBoardMapper ;
		this.myBoardAttachFileMapper = myBoardAttachFileMapper ;
	}

	//게시물 조회 - 목록
	@Override
	//public List<MyBoardVO> getBoardList() {
	public List<MyBoardVO> getBoardList(MyBoardPagingDTO myBoardPagingDTO) {
		//List<MyBoardVO> boardList = myBoardMapper.selectMyBoardList() ;
		//return boardList;
		return myBoardMapper.selectMyBoardList(myBoardPagingDTO) ;
	}
	
	@Override
	public long getRowAmountTotal(MyBoardPagingDTO myBoardPagingDTO) {
		return myBoardMapper.selectRowAmountTotal(myBoardPagingDTO) ;
	}
	

	//게시물 등록 - selectKey 이용
	@Transactional
	@Override
	public Long registerBoard(MyBoardVO myBoard) {
		log.info("서비스로 전달된 BoardVO: " + myBoard);
		
		myBoardMapper.insertMyBoardSelectKey(myBoard) ;
		//log.info("등록된 게시물의 bno 값: " + myBoard.getBno());
		
		//첨부파일이 없는 경우, 메서드 종료
		if (myBoard.getAttachFileList() == null || myBoard.getAttachFileList().size() <= 0) {
			return myBoard.getBno();
		}
		
		//첨부파일이 있는 경우, myBoard의 bno 값을 첨부파일 정보 VO에 저장 후, tbl_myAttachFiles 테이블에 입력
		myBoard.getAttachFileList().forEach(
				
				attachFile -> {
					attachFile.setBno(myBoard.getBno()) ;
					myBoardAttachFileMapper.insertAttachFile(attachFile);
				}
		);
		
		return myBoard.getBno();
	}

	//특정 게시물 조회(조회수 증가 고려)
	@Override
	public MyBoardVO getBoard(long bno) {
		//log.info("서비스로 전달된 bno: " + bno);
		myBoardMapper.updateBviewsCnt(bno);
		return myBoardMapper.selectMyBoard(bno) ;
	}
	
	//특정 게시물 조회 시, 게시물의 첨부파일 정보를 조회
	@Override
	public List<MyBoardAttachFileVO> getAttachFiles(long bno) {
		return myBoardAttachFileMapper.selectBoardAttachFileList(bno) ;
	}
	
	
	
	
	//게시물 조회: 게시물 조회 페이지 -> 게시물 수정 페이지 호출(by bno), 조회수 변화 없음
    //게시물 조회: 게시물 수정 후 -> 게시물 조회 페이지 호출(by bno), 조회수 증가 없음
	@Override
	public MyBoardVO getBoardDetailModify(long bno) {
		return myBoardMapper.selectMyBoard(bno) ;
	}
	
	

	//특정 게시물 수정
	@Transactional
	@Override
	public boolean modifyBoard(MyBoardVO myBoard) {
		log.info("서비스로 전달된 BoardVO: " + myBoard);
		
		long bno = myBoard.getBno() ;
		
		//게시물 변경 시, 기존 첨부파일이 삭제와 새로운 첨부파일 추가를 모두 고려하여, 기존 DB의 정보를 모두 삭제 후,
		//첨부파일 정보를 DB에 다시 추가하는 방식으로 처리합니다.
		//기존 DB 첨부파일 정보 삭제
		
		
		boolean boardModfyResult = myBoardMapper.updateMyBoard(myBoard) == 1 ;
		
		if ( (boardModfyResult && (myBoard != null && myBoard.getAttachFileList().size() > 0))) {
			myBoardAttachFileMapper.deleteBoardAllAttachFiles(bno);
			myBoard.getAttachFileList().forEach(
					
					attachFile -> {
						attachFile.setBno(bno) ;
						myBoardAttachFileMapper.insertAttachFile(attachFile) ;
					}
			);	
			
		}
		
		return boardModfyResult;
	}

	//특정 게시물 삭제 - 실제 삭제
	@Transactional
	@Override
	public boolean removeBoard(long bno) {
		myBoardAttachFileMapper.deleteBoardAllAttachFiles(bno);
		log.info("서비스로 전달된 bno: " + bno);
		return myBoardMapper.deleteMyBoard(bno) == 1;
	}

	//특정 게시물 삭제 요청 - 해당 글의 bdelFlag을 1로 수정
	@Transactional
	@Override
	public boolean setBoardDeleted(long bno) {
		log.info("서비스로 전달된 bno: " + bno);
		//myBoardAttachFileMapper.deleteBoardAllAttachFiles(bno);
		return myBoardMapper.updateBdelFlag(bno) == 1;
	}

	//게시물 삭제(관리자) – 사용자 삭제 요청된 게시물(bdelFlag = 1) 전체 삭제
//	@Override
//	public int removeAllDeletedBoard() {
//
//		return myBoardMapper.deleteAllBoardSetDeleted() ;
//	}

}
