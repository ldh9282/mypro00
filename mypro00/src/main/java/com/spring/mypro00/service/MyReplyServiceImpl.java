package com.spring.mypro00.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.mypro00.common.paging.MyReplyPagingCreatorDTO;
import com.spring.mypro00.common.paging.MyReplyPagingDTO;
import com.spring.mypro00.domain.MyReplyVO;
import com.spring.mypro00.mapper.MyBoardMapper;
import com.spring.mypro00.mapper.MyReplyMapper;

@Service
public class MyReplyServiceImpl implements MyReplyService {
	
	private MyReplyMapper myReplyMapper ;
	private MyBoardMapper myBoardMapper ;
	
	public MyReplyServiceImpl(MyReplyMapper myReplyMapper, MyBoardMapper myBoardMapper) {
		this.myReplyMapper = myReplyMapper ;
		this.myBoardMapper = myBoardMapper ;
	}

	//특정 게시물에 대한 댓글 목록 조회-페이징-전체댓글 수 고려
	@Override
	public MyReplyPagingCreatorDTO getReplyList(MyReplyPagingDTO myReplyPagingDTO) {
		
		System.out.println("컨트롤러부터 전달된 myReplyPagingDTO: " + myReplyPagingDTO);
		
		long replyTotalCnt = myReplyMapper.selectReplyTotalCnt(myReplyPagingDTO);
		
		int pageNum = myReplyPagingDTO.getPageNum() ;
		
		MyReplyPagingCreatorDTO myReplyPagingCreator = null ;
		
		if (replyTotalCnt == 0) {
			myReplyPagingDTO.setPageNum(1);
			System.out.println("댓글-서비스- 댓글이 없는 경우, pageNum은 1: 수정된 myReplyPaging: " + myReplyPagingDTO);
			
			myReplyPagingCreator =
					new MyReplyPagingCreatorDTO(
							0,
							myReplyPagingDTO, 
							myReplyMapper.selectMyReplyList(myReplyPagingDTO)) ;
			
		} else {
			
			if (pageNum == -10) {
				pageNum = (int) Math.ceil(replyTotalCnt/(myReplyPagingDTO.getRowAmountPerPage()*1.0)) ;
				
				myReplyPagingDTO.setPageNum(pageNum) ;
			}
			
			myReplyPagingCreator =
					new MyReplyPagingCreatorDTO(
							myReplyMapper.selectReplyTotalCnt(myReplyPagingDTO),
							myReplyPagingDTO, 
							myReplyMapper.selectMyReplyList(myReplyPagingDTO)) ;
			
		}
		
		System.out.println("컨트롤러로 전달할 myReplyPagingCreator: " + myReplyPagingCreator);
		
		return myReplyPagingCreator ;
	}

//	@Override
//	public long getReplyTotalCnt(MyReplyPagingDTO myReplyPagingDTO) {
//		
//		return myReplyMapper.selectReplyTotalCnt(myReplyPagingDTO);
//	}

	//특정 게시물에 대한 댓글 등록
	//@Transactional(propagation = Propagation.REQUIRED , isolation = Isolation.DEFAULT)
	@Transactional
	@Override
	public long registerReplyForBoard(MyReplyVO myReplyVO) {
		
		myReplyMapper.insertMyReplyForBoard(myReplyVO) ;
		myBoardMapper.updateBReplyCnt(myReplyVO.getBno(), 1);
		
		
		return myReplyVO.getRno();
	}

	//특정 댓글에 대한 답글 등록
	@Transactional
	@Override
	public long registerReplyForReply(MyReplyVO myReplyVO) {
		
		myReplyMapper.insertMyReplyForReply(myReplyVO) ;
		myBoardMapper.updateBReplyCnt(myReplyVO.getBno(), 1);
		
		return myReplyVO.getRno();
	}

	//특정 댓글 조회
	@Override
	public MyReplyVO getReply(long bno, long rno) {

		return myReplyMapper.selectMyReply(bno, rno);
	}
	
	//특정 게시물에 대한 특정 댓글/답글 수정
	@Override
	public int modifyReply(MyReplyVO myReply) {
 
		return myReplyMapper.updateMyReply(myReply);
	}

	//특정 댓글/답글 삭제: 삭제된 행수인 1 반환
	@Transactional
	@Override
	public int removeReply(long bno, long rno) {
		
		int delRowCnt = myReplyMapper.deleteMyReply(bno, rno) ;
		
		myBoardMapper.updateBReplyCnt(bno, -1);
		
		return delRowCnt;
	}

}
