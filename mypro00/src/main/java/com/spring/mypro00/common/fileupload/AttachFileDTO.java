package com.spring.mypro00.common.fileupload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachFileDTO {
	
	//브라우저로 전달하는 용도로 사용됩니다.
	
	private String fileName ;   //원본파일이름
	private String uploadPath ; //업로드 경로 : yyyy/MM/dd 형식의 문자열이 저장됨
	private String uuid ;
	private String fileType ;   //파일유형(이미지파일:I, 이미지가 아닌파일:F)
	private String repoPath = "C:/myupload";
	

}
