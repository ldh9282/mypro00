package com.spring.mypro00.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.mypro00.common.paging.MyBoardPagingCreatorDTO;
import com.spring.mypro00.common.paging.MyBoardPagingDTO;
import com.spring.mypro00.domain.MyBoardAttachFileVO;
import com.spring.mypro00.domain.MyBoardVO;
import com.spring.mypro00.service.MyBoardDAOService;
import com.spring.mypro00.service.MyBoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@AllArgsConstructor // myBoardService 필드에 서비스 객체를 주입하기 위해 사용됨
@RequestMapping(value="/myboard/*")
@Log4j
public class MyBoardController {
	
	public MyBoardService myBoardService ;
	
	public MyBoardDAOService myBoardDAOService ;
	
	//빈 주입 방법1: 생성자 주입방법(권장), 단일 생성자만 존재해야 함.
	//주의: 기본 생성자가 존재 시에 오류 발생
//	        private MyBoardService myBoardService;
//	        public MyBoardController(MyBoardService myBoardService) {
//	            this.myBoardService = myBoardService;
//	        }
	//
	//빈 주입 방법2: @Autowired(내부적으로 setter를 이용)
//	        @Autowired
//	        private MyBoardService myBoardService;
	//
	//빈 주입 방법3: setter 주입방법: lombok에서의 @Setter(onMethod_ = @Autowired) 이용하는 방법과 동일
//	        private MyBoardService myBoardService;
//	        @Autowired
//	        public void setMyBoardService(MyBoardService myBoardService) {
//	            this.myBoardService = myBoardService;
//	        }

	
	//목록 조회				GET /myboard/list
	@GetMapping("/list")
	public void showBoardList(MyBoardPagingDTO myBoardPagingDTO, Model model ) {
		
		//model.addAttribute("boardList", myBoardService.getBoardList(myBoardPagingDTO)) ;
		
		model.addAttribute("boardList", myBoardDAOService.getBoardListDAO(myBoardPagingDTO)) ;
		
		//long rowAmountTotal = myBoardService.getRowAmountTotal(myBoardPagingDTO) ;
		long rowAmountTotal = myBoardDAOService.getRowAmtTotalDAO(myBoardPagingDTO) ;
		
		MyBoardPagingCreatorDTO myBoardPagingCreatorDTO 
					= new MyBoardPagingCreatorDTO(rowAmountTotal, myBoardPagingDTO) ;
		model.addAttribute("pagingCreator", myBoardPagingCreatorDTO) ;
		
//		model.addAttribute("pagingCreator", new MyBoardPagingCreatorDTO(rowAmountTotal, myBoardPagingDTO));
		
		log.info("컨트롤러에서 생성된 MyBoardCreaingPagingDTO 객체 정보: " + myBoardPagingCreatorDTO.toString());
			
	}
	
	@PreAuthorize("isAuthenticated()")
	//등록 페이지 호출		GET /myboard/register
	@GetMapping("/register")
	public void showBoardRegisterPage() {
		
	}
	
	@PreAuthorize("isAuthenticated()")
	//등록 처리				POST /myboard/register
	@PostMapping("/register")
	public String registerNewBoard(MyBoardVO myBoard, RedirectAttributes redirectAttr) {
		
		log.info("컨트롤러 - 게시물등록(전달된 VO): " + myBoard.toString());
		System.out.println("============= attachInfo =============");
		if (myBoard.getAttachFileList() != null) {
			myBoard.getAttachFileList()
				.forEach(attachFile -> System.out.println("첨부 파일 정보:" + attachFile.toString()));
		}
		System.out.println("===========================================");
		
		
		Long bno = myBoardService.registerBoard(myBoard) ;
		log.info("등록된 게시물 bno: " + bno);
		
		redirectAttr.addFlashAttribute("result", bno) ;
		log.info("list.jsp로 전달되는 result: " + redirectAttr.getAttribute("result"));
		
		return "redirect:/myboard/list" ; 
	}
	
	//특정 게시물 조회		GET /myboard/detail
	@GetMapping("/detail")
	public void showBoardDetail(Long bno, @ModelAttribute("myBoardPagingDTO") MyBoardPagingDTO myBoardPagingDTO, Model model) {
		//log.info("사용자가 컨트롤러로 전달한 bno: " + bno) ;

		model.addAttribute("board", myBoardService.getBoard(bno)) ;
		//model.addAttribute("myBoardPagingDTO", myBoardPagingDTO) ;
		//log.info("컨트롤러: 서비스로부터 전달된 MyBoardVO: " + model.getAttribute("board")) ;
	}
	
	
	@GetMapping(value="/getFiles" , produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public ResponseEntity<List<MyBoardAttachFileVO>> showAttachFiles(long bno){
		
		return new ResponseEntity<List<MyBoardAttachFileVO>>(myBoardService.getAttachFiles(bno), HttpStatus.OK) ;
		
	}
	
	
	
	
	//@PreAuthorize("isAuthenticated() && principal.username == #bwriter"): detail.jsp의 수정버튼 클릭 이벤트 화면으로부터 bwriter 값을 받아서, 매개변수에 저장하도록 수정
	//수정 페이지 호출		GET /myboard/register
	@GetMapping("/modify")
	public void showBoardModify(Long bno, @ModelAttribute("myBoardPagingDTO") MyBoardPagingDTO myBoardPagingDTO, Model model) {
		log.info("컨트롤러 - 게시물 수정 페이지 호출: "+ bno);
		log.info("컨트롤러 - 전달된 MyBoardPagingDTO: "+ myBoardPagingDTO);
		
		model.addAttribute("board", myBoardService.getBoardDetailModify(bno)) ;
	}
	
	
	//수정 후, 상세 페이지 호출		GET /myboard/register
	@GetMapping("/detailmod")
	public String showBoardDetailMod(Long bno, @ModelAttribute("myBoardPagingDTO") MyBoardPagingDTO myBoardPagingDTO, Model model) {
		log.info("컨트롤러 - 게시물 수정 페이지 호출: "+ bno);
		log.info("컨트롤러 - 전달된 MyBoardPagingDTO: "+ myBoardPagingDTO);
		
		model.addAttribute("board", myBoardService.getBoardDetailModify(bno)) ;
		
		return "/myboard/detail" ;
	}
	
	@PreAuthorize("isAuthenticated() && principal.username == #myBoard.bwriter")
	//특정 게시물 수정		POST /myboard/modify
	@PostMapping("/modify")
	public String modifyBoard(MyBoardVO myBoard, RedirectAttributes redirectAttr,
			                  MyBoardPagingDTO myBoardPagingDTO) {
		
		if(myBoardService.modifyBoard(myBoard)) {
			redirectAttr.addFlashAttribute("result", "successModify") ;
			
		}
		//return "redirect:/myboard/detail?bno=" + myBoard.getBno() ;
		
		redirectAttr.addAttribute("bno", myBoard.getBno()) ;
//		redirectAttr.addAttribute("pageNum", myBoardPagingDTO.getPageNum());
//		redirectAttr.addAttribute("rowAmountPerPage", myBoardPagingDTO.getRowAmountPerPage()) ;
//		redirectAttr.addAttribute("scope", myBoardPagingDTO.getScope()) ;
//		redirectAttr.addAttribute("keyword", myBoardPagingDTO.getKeyword()) ;
//		return "redirect:/myboard/detailmod" ;
		return "redirect:/myboard/detailmod"  + myBoardPagingDTO.addPagingParamsToURI();
	}
	
	@PreAuthorize("isAuthenticated() && principal.username == #bwriter")
	//특정 게시물 삭제		POST /myboard/remove
	@PostMapping("/remove")
	public String removeBoard(Long bno, String bwriter, RedirectAttributes redirectAttr, MyBoardPagingDTO myBoardPagingDTO) {

		//첨부파일 정보가 저장된 리스트 객체 생성
		List<MyBoardAttachFileVO> attatchFileList = myBoardService.getAttachFiles(bno) ;
		
		//DB로부터 게시물(첨부파일 포함) 정보 삭제
		if(myBoardService.removeBoard(bno)) {
			removeAttachFiles(attatchFileList) ;
			redirectAttr.addFlashAttribute("result", "successRemove") ;
			
		} else {
			redirectAttr.addFlashAttribute("result", "failRemove") ;
		}
		
		redirectAttr.addAttribute("pageNum", myBoardPagingDTO.getPageNum());
		redirectAttr.addAttribute("rowAmountPerPage", myBoardPagingDTO.getRowAmountPerPage()) ;
		redirectAttr.addAttribute("scope", myBoardPagingDTO.getScope()) ;
		redirectAttr.addAttribute("keyword", myBoardPagingDTO.getKeyword()) ;
		
		
		return "redirect:/myboard/list" ;
	}


	//@PreAuthorize("principal.username == #bwriter")
	@PreAuthorize("isAuthenticated() && principal.username == #bwriter")
	//특정 게시물 삭제의뢰		POST /myboard/delete
	@PostMapping("/delete")
	public String deleteBoard(@RequestParam("bno") Long bno, 
							  String bwriter,
							  //@RequestParam("bwriter") String bwriter, 
							  RedirectAttributes redirectAttr, 
							  MyBoardPagingDTO myBoardPagingDTO) {
		
		System.out.println("bwriter: " + bwriter);
		
		//첨부파일 정보가 저장된 리스트 객체 생성
		List<MyBoardAttachFileVO> attatchFileList = myBoardService.getAttachFiles(bno) ;
		
		
		if(myBoardService.setBoardDeleted(bno)) {
			//removeAttachFiles(attatchFileList) ; //실제 파일 삭제를 막기 위해서 주석처리 됨
			redirectAttr.addFlashAttribute("result", "successRemove") ;
		} else {
			redirectAttr.addFlashAttribute("result", "failRemove") ;
		}
		
		redirectAttr.addAttribute("pageNum", myBoardPagingDTO.getPageNum());
		redirectAttr.addAttribute("rowAmountPerPage", myBoardPagingDTO.getRowAmountPerPage()) ;
		redirectAttr.addAttribute("scope", myBoardPagingDTO.getScope()) ;
		redirectAttr.addAttribute("keyword", myBoardPagingDTO.getKeyword()) ;
		
		
		return "redirect:/myboard/list" ;
	}
  

	private void removeAttachFiles(List<MyBoardAttachFileVO> attachFileList) {
		
		if (attachFileList == null || attachFileList.size() == 0) {
			return ;
		}
		
		attachFileList.forEach(attachFile -> {
			Path file = Paths.get(attachFile.getRepoPath() + "/" +
								  attachFile.getUploadPath() + "/" +
								  attachFile.getUuid() + "_" +
								  attachFile.getFileName()
		                         ) ;
			try {
				Files.deleteIfExists(file) ;
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbnail = Paths.get(attachFile.getRepoPath() + "/" +
							  				   attachFile.getUploadPath() + "/s_" +
							  				   attachFile.getUuid() + "_" +
							  				   attachFile.getFileName()
				           ) ;
					Files.delete(thumbnail) ;
				}
				
			} catch (Exception e) {
				System.out.println("오류: " + e.getMessage());
			}
			
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
