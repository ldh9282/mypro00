package com.spring.mypro00.common.filedownload;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@Log4j
public class FileDownloadAjaxController {
	
	@GetMapping(value="/fileDownloadAjax", produces = "application/octet-stream")
	@ResponseBody
	public ResponseEntity<Resource> fileDownloadActionAjax(@RequestHeader("User-Agent") String userAgent, 
														   String fileName){
		//log.info("브라우저로 요청에서 전달된 User-Agent 해더정보: "+ userAgent);
		//log.info("처리 전 파일이름: " + fileName);
		
		Resource resource = new FileSystemResource(fileName);
		//log.info("resource :" + resource);
		
		if(!resource.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND) ;
			
		} 
		
		String resourceFileName = resource.getFilename() ;
		//log.info("UUID 제거 전 resourceFileName :" + resourceFileName);
		
		//UUID가 제거된 파일이름
		resourceFileName = resourceFileName.substring(resourceFileName.indexOf("_") + 1) ;
		//log.info("UUID 제거 후 resourceFileName :" + resourceFileName);
		
		HttpHeaders httpHeaders = new HttpHeaders() ;
		
		String downloadFileName  = null ;
		
		if ( userAgent.contains("Trident") || userAgent.contains("MSIE") ||
				userAgent.contains("Edge") || userAgent.contains("Edg")) {
			
			//downloadFileName = resourceFileName ;
			
			try {
				downloadFileName = URLEncoder.encode(resourceFileName, "utf-8");

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//log.info("downloadFileName: " + downloadFileName) ;
			
			
		} else { 
			
			try {
				downloadFileName = new String(resourceFileName.getBytes("utf-8"), "ISO-8859-1") ;
				//log.info("Chrome에서의 파일이름: "+ downloadFileName);
			
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		httpHeaders.add("Content-Disposition", "attachment; filename=" + downloadFileName) ;
		
		
		
		return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK) ;
	}

}
