package com.spring.mypro00.mapper;

import java.util.List;

import com.spring.mypro00.domain.MyBoardAttachFileVO;

public interface MyBoardAttachFileMapper {
	
	public void insertAttachFile(MyBoardAttachFileVO boardAttachFile) ;
	
	public List<MyBoardAttachFileVO> selectBoardAttachFileList(long bno) ;
	
	public void deleteAttachFile(String uuid) ;
	
	public void deleteBoardAllAttachFiles(long bno) ;
	
	public List<MyBoardAttachFileVO> selectAttachFilesBeforeOneDay() ;

}
