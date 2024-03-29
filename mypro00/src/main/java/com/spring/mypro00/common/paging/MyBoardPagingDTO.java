package com.spring.mypro00.common.paging;

import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyBoardPagingDTO {
	
	private int pageNum ;
	private int rowAmountPerPage ;
	private String scope ;
	private String keyword ;
	
	
	//검색범위 값 처리를 위한 메소드: 화면에서 선택된 TCW 값을 ["T", "C", "W"] 배열로 반환
	//이 메서드는 Mybatis 엔진에 의해서 자동으로 호출되어 사용됨(SQL 매퍼 파일의 SELECT문에서 설명함)
	public String[] getScopeArray() {
		return this.scope == null ? new String[] {} : this.scope.split("");
	}
	
	public String addPagingParamsToURI() {
		
		UriComponentsBuilder uriComponentsBuilder = 
				UriComponentsBuilder.fromPath("")
									.queryParam("pageNum", this.pageNum)
									.queryParam("rowAmountPerPage", this.rowAmountPerPage)
									.queryParam("scope", this.scope)
									.queryParam("keyword", this.keyword) ;
		
		String uriString = uriComponentsBuilder.toUriString() ;
		System.out.println("생성된 파라미터 추가 URI 문자열: " + uriString);
		return uriString ;
		
		//return uriComponentsBuilder.toUriString() ;
		
	}
	
	
	public MyBoardPagingDTO() {
		this.pageNum = 1 ;
		this.rowAmountPerPage = 10 ;
	}
	
	public MyBoardPagingDTO(int pageNum) {
		if(pageNum <= 0) {
			this.pageNum = 1 ;
			
		} else {
			this.pageNum = pageNum ;
		}
		
		this.rowAmountPerPage = 10 ;
	}

	public MyBoardPagingDTO(int pageNum, int rowAmountPerPage) {
		if(pageNum <= 0) {
			this.pageNum = 1 ;
			
		} else {
		
			this.pageNum = pageNum ;
		}
		
		if(rowAmountPerPage <= 0) {
			this.rowAmountPerPage = 10 ;
			
		} else {
		
			this.rowAmountPerPage = rowAmountPerPage ;
		}

	}
	
	

}
