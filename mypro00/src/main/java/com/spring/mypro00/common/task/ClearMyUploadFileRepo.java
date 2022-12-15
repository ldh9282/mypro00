package com.spring.mypro00.common.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.spring.mypro00.domain.MyBoardAttachFileVO;
import com.spring.mypro00.mapper.MyBoardAttachFileMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClearMyUploadFileRepo {
	
	private MyBoardAttachFileMapper myBoardAttachFileMapper ;
	
	//하루 전 문자열 생성 메서드
	private String getStrYesterday() {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM") ;
		
		//달력객체(년/월/일)를 변수에 대입
		Calendar calendar = Calendar.getInstance() ;
		
		//달력에서의 하루전 날짜를 생성해서 달력 객체에 추가
		calendar.add(Calendar.DATE, -1) ;
		
		//달력객체에 설정된 밀리세컨트 값을 가져와서 날짜형식 문자열로 변환
		String strYesterday = simpleDateFormat.format(calendar.getTime()) ;
		
		//return strYesterday.replace("/", File.separator) ;
		return strYesterday ;
		
	}
	
	@Scheduled(cron = "0 20 9 * * *")
	public void clearNeedlessFiles() {
		
		String uploadRepoDir = "C:/myupload" ;
		
		//데이터베이스에 저장된 하루 전 첨부파일 정보목록 생성(삭제하면 않되는 파일들)
		List<MyBoardAttachFileVO> doNotDeleteFileList = myBoardAttachFileMapper.selectAttachFilesBeforeOneDay();
		
		//List<MyBoardAttachFileVO> --> List<Path> 로 변환
		List<Path> doNotDeleteFilePathList
			= doNotDeleteFileList   //ArrayList 객체
			  .stream()    //스트림(Stream<MyBoardAttachFileVO>)으로 변환
		      .map(myBoardAttachFile -> Paths.get(myBoardAttachFile.getRepoPath() + "/" +
		    		                              myBoardAttachFile.getUploadPath() + "/" +
		    		                              myBoardAttachFile.getUuid() + "_" +
		    		                              myBoardAttachFile.getFileName())
		    	   ) //map-end  : MyBoardAttachFileVO 하나하나가 -> Path 객체로 변환되어 저장된 Stream 객체
		      .collect(Collectors.toList()) ; //Stream 객체 --> List 객체로 변환
		
		//원본파일(썸네일포함)의 Path 객체가 저장된 List 객체가 준비됨: doNotDeleteFilePathList
		doNotDeleteFileList
		.stream()   //스트림(Stream<MyBoardAttachFileVO>)으로 변환
		.filter(myBoardAttachFile -> myBoardAttachFile.getFileType().equals("I"))
		.map(myBoardAttachFile -> Paths.get(myBoardAttachFile.getRepoPath() + "/" +
                							myBoardAttachFile.getUploadPath() + "/s_" +
                							myBoardAttachFile.getUuid() + "_" +
                							myBoardAttachFile.getFileName()))
		//.collect(Collectors.toList()) ;
		.forEach(doNotDeleteFilePath -> doNotDeleteFilePathList.add(doNotDeleteFilePath));
		
		//지우면 않되는 파일 목록을 콘솔에 표시
		System.out.println("=================================================================");
		doNotDeleteFilePathList
		.forEach(doNotDeleteFilePath -> System.out.println(doNotDeleteFilePath));
		
		//하루 전 날짜경로가 저장된 파일 객체
		File dirBeforeOneDay =
				Paths.get(uploadRepoDir + "/" + getStrYesterday()).toFile(); 
		
		//하루전 폴더에 있는 파일 중, DB에 정보가 없는 파일만 저장된 배열 생성
		File[] needlessFileArray =
				dirBeforeOneDay
				.listFiles(eachFile -> doNotDeleteFilePathList.contains(eachFile.toPath()) == false ) ;
		
		//필요없는 파일 삭제
		if(needlessFileArray == null) {
			System.out.println("========== 삭제할 파일이 없습니다. ==========");
			return ;
			
		} else {
			System.out.println("================ 다음의 파일들이 삭제됩니다. ================");
			for(File needlessFile : needlessFileArray) {
				System.out.println("필요없는파일: " + needlessFile.getAbsolutePath());
				needlessFile.delete() ;
				
			}
		}
	}

	
	
}
