package sample.chap04.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;
import sample.chap04.domain.SampleDTO;
import sample.chap04.domain.SampleDTOList;
import sample.chap04.domain.TodoDTO;

@Controller
@RequestMapping("/sample/*" )
@Log4j
public class SampleController {
	
//	@RequestMapping(value = "/sample/1" , method = RequestMethod.GET)
//	public void basic() {
//		log.info("basic===========") ;
//	}
//	
//	
//	@RequestMapping("/sample/2")
//	public void basic2() {
//		log.info("basic===========") ;
//	}
	
	
//	@RequestMapping(value="" , method= {RequestMethod.GET, RequestMethod.POST})
//	public void basic() {
//		log.info("basic======================================");
//	}
	
	@RequestMapping("/1")
	public void basic1() {
		log.info("basic11111========================================");
		
	}
	
	@RequestMapping("/2")
	public void basic2() {
		log.info("basic2222========================================");
		
	}
	
	@RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
	public void basicGet() {
		log.info("basicGet========================================");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basicGet22222========================================");
	}

	@PostMapping("/basicOnlyPost")
	public void basicPost() {
		log.info("basicPost22222========================================");
	}
	
	@GetMapping("/ex01")
	public String myEx01(SampleDTO dto) {
		System.out.println("dto.name: " + dto.getName());
		System.out.println("dto.age: " + dto.getAge());
		log.info("=============" + dto);
		return "ex01" ;
	}
	
	@GetMapping("/ex02")
	public String myEx02(String name, int age) {
		log.info("name으로 전달된 값:" + name);
		log.info("age로 전달된 값:" + age);
		
		return "ex02" ;
	}
	
	@GetMapping("/ex022")
	public String myEx022(@RequestParam("name") String name1, 
			              @RequestParam("age") String age1) {
		log.info("name1으로 전달된 값:" + name1);
		log.info("age1로 전달된 값:" + age1);
		
		return "ex02" ;
	}
	
	//여러 개의 값을 ArrayList 또는 배열 타입의 매개변수에 저장하려면, @RequestParam을 사용해야만 합니다.
	@GetMapping("/ex02List")
	public String myEx02List(@RequestParam("ids") ArrayList<String> myIds) {
		log.info("myIds로 전달된 값들: " + myIds);
		return "ex02List" ;
	}
	
	@GetMapping("/ex022List")  //값 전달이 않됨
	public String myEx022List(ArrayList<String> ids) {
		log.info("ids로 전달된 값들: " + ids);
		return "ex02List" ;
	}
	
	@GetMapping("/ex02Array")
	public String myEx02Array(@RequestParam("ids") String[] ids) {
		log.info("ids로 전달된 값들: " + Arrays.toString(ids));
		return "ex02List" ;
	}
	
	@GetMapping("/ex02dto")
	public String ex02dto(SampleDTO dto) {
		log.info("dto 매개변수에 저장된 값:" + dto);
		return "ex02dto";
	}
	
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list로 전달된 값들: " + list);
		
		return "ex02Bean" ;
	}
	
	//localhost:8080/mypro00/sample/ex02Bean?list[0].name=aaaa&list[1].name=bbb&list[2].age=444
	//localhost:8080/mypro00/sample/ex02Bean?list%5B0%5D.name=aaaa&list%5B1%5D.name=bbb&list%5B2%5D.age=444
	
	
	
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo에 전달된 객체: " + todo);
		return "ex03" ;
	}
	
	//localhost:8080/mypro00/sample/ex03?title=신상현&dueDate=2021-11-3
	
	
	//JSP까지 값을 전달하는 @Controller 어노테이션 클래스의 메서드 동작에 대해서 학습합니다.
	//기본타입(int, String, Integer, 배열포함) 매개변수로 전달된 값은 JSP까지는 전달되지 못합니다.
	@GetMapping("/ex04")
	//public String ex04(SampleDTO dto, @RequestParam("page")  String page) { //page가 jsp 전달 안됨
	public String ex04(SampleDTO dto, @ModelAttribute("page")  String page) {
		log.info("전달된 dto: " + dto);
		log.info("전달된 page: " + page);
		return "/sample/ex04" ;
	}
	
	//웹브라우저 URL: http://localhost:8080/mypro00/sample/ex04?name=%EC%8B%A0%EC%83%81%ED%98%84&age=53&page=9
	//이클립스 콘솔
	//INFO : sample.chap06.controller.SampleController - 전달된 dto: SampleDTO(name=신상현, age=53)
	//INFO : sample.chap06.controller.SampleController - 전달된 page: 9
	//웹브라우저 표시내용
	//sampleDTO: SampleDTO(name=신상현, age=53)
	//Page:   <--int, Integer 사용시
	
	//@ModelAttribute를 이용하여 기본타입(int, String, Integer, 배열포함) 매개변수로 전달된 값을 JSP까지 전달
	@GetMapping("/ex05")
	//public String ex05(SampleDTO dto, String page) {  //page 값이 JSP까지 전달되지 않음
	public String ex05(SampleDTO dto, @ModelAttribute("page") String page) {     //page 값이 JSP까지 전달됨
	//public String ex05(SampleDTO dto, @ModelAttribute("page1") String page) {  //콘솔에는 page 값이 표시됨
		                                                                         //JSP에는 표시되지 않음
		log.info("전달된 dto: " + dto);
		log.info("전달된 page: " + page);
		return "sample/ex04" ;
	}
	
	//http://localhost:8080/mypro00/sample/ex05?name=%EC%8B%A0%EC%83%81%ED%98%84&age=53&page=9
	
	
	@GetMapping("/ex055")
	public String ex055(SampleDTO dto, String page, Model model) {  ///콘솔에는 page 값이 표시됨
        //JSP에는 표시되지 않음
		//JSP에는 표시되지 않음
		model.addAttribute("page1", page) ;
		log.info("전달된 dto: " + dto);
		log.info("전달된 page: " + page);
		return "sample/ex04" ;
	}
	
	
	@GetMapping("/ex06")
	public String ex06() {
	
		String name = null ;
	
		try {
			name = URLEncoder.encode("홍길동", "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/sample/ex05?name=" + name + "&age=53&page=9" ;  //URL에 컨텍스트이름을 명시하면 않됨
		
	}
	
	@GetMapping("/ex066")
	public String ex066(RedirectAttributes rttr) {
		rttr.addAttribute("name", "홍길동") ;
		rttr.addAttribute("age", 53) ;
		rttr.addAttribute("page", 9) ;
		return "redirect:/sample/ex05" ;
	}
	
	
	//url 기반으로 jsp 파일을 찾아서, dto 객체를 반환
	//@ResponseBody를 사용하지 않은 경우, JSP 페이지로 값이 전달됩니다.
	@GetMapping("/ex07")
	public SampleDTO ex07() {
		log.info("/ex07=====================================");
		SampleDTO dto = new SampleDTO() ;
		dto.setName("홍길동") ;
		dto.setAge(10) ;
		
		return dto ;
	}
	
	//반환타입을 SampleDTO로 적은 경우
	//웹브라우저에서 http://localhost:8080/mypro00/sample/ex07로 요청하여
	//오류없이 웹브라우저에 홍길동10이 표시됨
	
	
	//@ResponseBody: REST API 어노테이션, JSP 파일 호출이 없음
	@GetMapping("/ex077")
	@ResponseBody
	public  SampleDTO ex077() {
		log.info("/ex077=====================================");
		SampleDTO dto = new SampleDTO() ;
		dto.setName("홍길동") ;
		dto.setAge(10) ;
		
		return dto ;
	}
	
	//반환타입을 @ResponseBody SampleDTO로 적은 경우
	//웹브라우저에서 http://localhost:8080/mypro00/sample/ex07로 요청하여
	//오류없이 웹브라우저에 홍길동10이 표시됨
	
	
	//반환타입이 ResponseEntity<E> 인 경우, JSP 호출을 않함
	@GetMapping("/ex08")  
	public ResponseEntity<String> ex08() {
		log.info("/ex08=====================================");
		// {"name": "AAA"}
		
			
		String msg = "{\"name\": \"AAA\"}" ;
		
		//HttpHeaders httpHeaders = new HttpHeaders() ;
		//httpHeaders.add("Context-Type", "application/xml;charset=utf-8") ;
		
		//return new ResponseEntity<String>(msg, httpHeaders, HttpStatus.NOT_FOUND) ;
		return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND) ;
	}
	
	
	//파일 UPLOAD 페이지 요청
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload============================");
	}
	
	//파일UPLOAD 수행 메서드
	//org.springframework.web.multipart.MultipartFile;
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files, SampleDTO sampleDTO ) {
		
		log.info("name: " + sampleDTO.getName()) ;
		log.info("age: " + sampleDTO.getAge()) ;
		
		String uploadFolder = "C:/myupload" ;
		
		files.forEach(file -> {
			log.info("--파일업로드 호출 및 시작------------------") ;
			log.info("--업로드 파일이름: " + file.getOriginalFilename()) ;
			log.info("--업로드 파일크기: " + file.getSize()) ;
			
			if(file.getSize() > 0) {
				File saveFile = new File(uploadFolder, file.getOriginalFilename()) ; 
				
				try {
					file.transferTo(saveFile);  //서버에 저장.
				} catch (IllegalStateException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
						
			}
		});
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
