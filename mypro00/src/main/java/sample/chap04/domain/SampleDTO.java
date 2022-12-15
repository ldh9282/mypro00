package sample.chap04.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SampleDTO {
	
	private String name ;
	private Integer age ;
	
	public SampleDTO() {
		
	}

//	public SampleDTO(String name, Integer age) {
//	
//		this.name = name ;
//		this.age = age ;
//	}
	
	
	
//	public SampleDTO() {
//		this.name = "홍길동" ;
//		this.age = 23 ;
//		System.out.println("SampleDTO의 기본 생성자 입니다.");
//	}
//	
	

}
