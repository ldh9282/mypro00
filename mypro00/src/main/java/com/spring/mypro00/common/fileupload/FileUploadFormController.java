package com.spring.mypro00.common.fileupload;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class FileUploadFormController {
	//private static final Logger log = LoggerFactory.getLogger(FileUploadFormController.class);
	
	//저장경로 설정 (Windows 환경이므로 경로구분자를 \\로 지정)
	private String uploadFileRepoDir = "C:\\myupload" ;
	
	//form을 통한 다중 파일 업로드 
	
	//1. 파일 업로드 요청 JSP 페이지 호출
	@GetMapping(value="/fileUploadForm")
	public String callFileUploadFormPage() {
		log.info("upload Form 시작=====================");
		return "sample/fileUploadForm" ;
		
	}
	
	//2.업로드 처리 및 결과 페이지 호출
	@PostMapping("/fileUploadFormAction")
	public String fileUploadActionPost(MultipartFile[] uploadFiles, Model model,
			                         @ModelAttribute("ename") String ename) {
		
		for(MultipartFile multipartFile : uploadFiles) {
			
			log.info("=================================");
			log.info("Upload File Name: "+ multipartFile.getOriginalFilename());
			log.info("Upload File Size: "+ multipartFile.getSize());
			
			//File saveUploadFile = new File(uploadFileRepoDir, multipartFile.getOriginalFilename()) ;
			String strUploadFileName = multipartFile.getOriginalFilename() ;
			
			strUploadFileName = strUploadFileName.substring(strUploadFileName.lastIndexOf("\\") + 1) ;
			
			File saveUploadFile = new File(uploadFileRepoDir, strUploadFileName) ;
			
			try {
				multipartFile.transferTo(saveUploadFile);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			} 
			
		}
		
		return "sample/fileUploadFormAction" ;
	}
	
	
}
