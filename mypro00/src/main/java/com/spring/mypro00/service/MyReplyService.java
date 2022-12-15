package com.spring.mypro00.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.mypro00.common.paging.MyReplyPagingCreatorDTO;
import com.spring.mypro00.common.paging.MyReplyPagingDTO;
import com.spring.mypro00.domain.MyReplyVO;

public interface MyReplyService {
	
	//특정 게시물에 대한 댓글 목록 조회-페이징 고려
	public MyReplyPagingCreatorDTO getReplyList(MyReplyPagingDTO myReplyPagingDTO) ;
	
	//특정 게시물의 댓글 총 개수확인
	//public long getReplyTotalCnt(MyReplyPagingDTO myReplyPagingDTO) ;
	
	//특정 게시물에 대한 댓글 등록: rno 반환
	public long registerReplyForBoard(MyReplyVO myReplyVO) ;
	
	//특정 댓글에 대한 답글 등록: rno 반환
	public long registerReplyForReply(MyReplyVO myReplyVO) ;
	
	//특정 게시물에 대한 특정 댓글/답글 조회
	public MyReplyVO getReply(long bno, long rno);
	
	//특정 게시물에 대한 특정 댓글/답글 수정
	public int modifyReply(MyReplyVO myReply) ;
	
	//특정 게시물에 대한 특정 댓글/답글 삭제
	public int removeReply(long bno, long rno) ;

}
