package com.spring.mypro00.common.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class FileUploadAjaxController {
	
	//저장경로 설정 (Windows 환경이므로 경로구분자를 /로 지정)
	private String uploadFileRepoDir = "C:/myupload" ;
	
	//Ajax를 통한 다중 파일 업로드
	//1. 파일 업로드 요청 JSP 페이지 호출
	@GetMapping(value="/fileUploadAjax")
	public String callFileUploadFormPage() {
		//log.info("upload Ajax 시작=====================");
		return "sample/fileUploadAjax" ;
		
	}
	
	//추가고려사항 3: 업로드 파일이 저장될 폴더로 사용되는 날짜 형식 문자열 생성
	private String getDatePathName() {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd") ;
		
		Date date = new Date() ;
		
		String strDatePathName = simpleDateFormat.format(date) ;
		
		//return strDatePathName.replace("/", File.separator) ;
		return strDatePathName ;
	}
	
	//추가고려사항 4: 업로드 파일의 이미지파일 유무 확인 메서드
	private boolean checkIsImageForUploadFile(File uploadFile) {
		
		try {
			String strContentType = Files.probeContentType(uploadFile.toPath()) ;
			log.info("업로드파일의 ContentType: " + strContentType);
			
			return strContentType.startsWith("image") ;
//			return Files.probeContentType(uploadFile.toPath()).startsWith("image") ;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false ;
	}
	
	
	//2. 업로드 요청 파일-저장 및 결과 데이터 전송
	@PostMapping(value = "fileUploadAjaxAction" ,produces = {"application/json; charset=utf-8"})
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> fileUploadActionPost(MultipartFile[] uploadFiles) {
		
		List<AttachFileDTO> listAttachInfo = new ArrayList<AttachFileDTO>() ;
		
		
		
		//추가고려사항 3: 업로드 파일이 저장될 날짜 형식 경로 생성
		//File fileUploadPath = new File(uploadFileRepoDir, getDatePathName()) ;
		String strDatePathName = getDatePathName() ;
		String strFileUploadPath =  uploadFileRepoDir + "/" + strDatePathName ;
		
		//File fileUploadPath = new File(uploadFileRepoDir, strDatePathName) ; 
		File fileUploadPath = new File(strFileUploadPath) ;
		
		if(!fileUploadPath.exists()) {
			fileUploadPath.mkdirs() ;
		}

		
		for(MultipartFile multipartFile : uploadFiles ) {
			
			AttachFileDTO attachInfo = new AttachFileDTO() ;
			
			attachInfo.setUploadPath(strDatePathName) ;
			attachInfo.setRepoPath(uploadFileRepoDir) ;
			
			//log.info("=================================");
			//log.info("Upload File Name: "+ multipartFile.getOriginalFilename());
			//log.info("Upload File Size: "+ multipartFile.getSize());
			
			//추가고려사항 1
			//[Edge, IE 오류 해결] multipartUploadFile.getOriginalFilename()에서 업로드 파일이름만 추출
			String strUploadFileName = multipartFile.getOriginalFilename() ;
			strUploadFileName = strUploadFileName.substring(strUploadFileName.lastIndexOf("\\") + 1) ;
			
			attachInfo.setFileName(strUploadFileName) ;
			
			//추가고려사항 2: UUID를 이용한 고유한 파일이름 적용
			UUID uuid = UUID.randomUUID() ;
			
			attachInfo.setUuid(uuid.toString()) ;			
			strUploadFileName = uuid.toString() + "_" + strUploadFileName ;
			//log.info("uuid포함파일이름: " + strUploadFileName) ;
			
			
			//File saveUploadFile = new File(uploadFileRepoDir, strUploadFileName) ;
			//추가고려사항 3: 최종 업로드 파일경로를 가진 File 객체 생성
			File saveUploadFile = new File(fileUploadPath, strUploadFileName) ;
			
			try {
				
				//추가고려사항 4: 썸네일 생성
				if (checkIsImageForUploadFile(saveUploadFile)) {
					attachInfo.setFileType("I") ;
					FileOutputStream fileOutputStream = 
							new FileOutputStream(new File(fileUploadPath, "s_" + strUploadFileName)) ;
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), 
							                      fileOutputStream, 
							                      20, 20) ;
					
					fileOutputStream.flush() ;
					fileOutputStream.close() ;					
				
				} else {
					attachInfo.setFileType("F") ;	
				}
				
				multipartFile.transferTo(saveUploadFile);
				
			} catch (IllegalStateException | IOException e) {
				
				log.error(e.getMessage());
			}
			
			listAttachInfo.add(attachInfo) ;
			

		} //for-end

		return new ResponseEntity<List<AttachFileDTO>> (listAttachInfo, HttpStatus.OK) ;
		
	}
	
	@GetMapping("/displayThumbnail")
	@ResponseBody
	public ResponseEntity<byte[]> sendThumbNailFile(String fileName) {
		
		File file = new File(fileName) ;
		
		ResponseEntity<byte[]> result = null ;
		
		HttpHeaders header = new HttpHeaders() ;
		
		try {
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result ;
	}
	
	//서버에 업로드 된 파일 삭제
	@PostMapping("/deleteUploadedFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String fileType) {
		//uuid가 포함된 파일이름을 전달받음.
		//일반파일: 파일이름이 전달됨
		//이미지파일: 썸네일의 파일이름이 전달됨
		//log.info("전달된 fileName: " + fileName);
		
		File delFile = null ;
		
		try {
			delFile = new File (URLDecoder.decode(fileName, "utf-8")) ;
			//log.info("decoded deleting fileName: " + delFile.toString());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND) ;
		}
		
		delFile.delete() ; //일반파일, 썸네일 파일 삭제
		
		if(fileType.equals("I")) {
			
//			String filePath = fileName.substring(0, fileName.lastIndexOf("/") + 1 ) ;
//			String _fileName = "s_" + fileName.substring(fileName.lastIndexOf("/") + 1) ;
//			String thumbNail = filePath + _fileName ;
			//System.out.println("썸네일이름: " + thumbNail);
//			try {
//				//delFile = new File(URLDecoder.decode(thumbNail, "utf-8")) ;
//				delFile = new File(URLDecoder.decode(thumbNail, "utf-8")) ;
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			String imageFileName = delFile.getAbsolutePath().replace("s_", "") ;
			delFile = new File(imageFileName) ;
			
			delFile.delete() ; //원본이미지 파일 삭제
		}
		
		return new ResponseEntity<String>("SuccessFileDelete", HttpStatus.OK) ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
