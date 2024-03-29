package com.spring.mypro00.controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.mypro00.common.paging.MyReplyPagingCreatorDTO;
import com.spring.mypro00.common.paging.MyReplyPagingDTO;
import com.spring.mypro00.domain.MyReplyVO;
import com.spring.mypro00.service.MyReplyService;

import lombok.AllArgsConstructor;

@RequestMapping(value= {"/replies/*"})
@RestController
@AllArgsConstructor
public class MyReplyController {
	
	private MyReplyService myReplyService ;
	

	//게시물에 대한 댓글 목록 조회:      			GET /replies/pages/{bno}/{page}
	@GetMapping( value="/{bno}/page/{page}" ,
			     produces= {"application/json; charset=utf-8"} )
	public ResponseEntity<MyReplyPagingCreatorDTO> showReplyList(@PathVariable("bno") long bno, @PathVariable("page") Integer pageNum) {
		
		//MyReplyPagingDTO myReplyPaging = new MyReplyPagingDTO(bno, pageNum) ;
		//MyReplyPagingCreatorDTO replyPagingCreator = myReplyService.getReplyList(myReplyPaging) ;
	
		MyReplyPagingCreatorDTO replyPagingCreator = 
								myReplyService.getReplyList(new MyReplyPagingDTO(bno, pageNum)) ;
		
//		ResponseEntity<MyReplyPagingCreatorDTO> responseEntity =
//				new ResponseEntity<MyReplyPagingCreatorDTO>(replyPagingCreator, HttpStatus.OK) ;
//		
//		return responseEntity ;
		
		return new ResponseEntity<MyReplyPagingCreatorDTO>(replyPagingCreator, HttpStatus.OK) ;
	}
	
	//게시물에 대한 댓글 등록(rno 반환):       	POST /replies/{bno}/new
	@PreAuthorize("isAuthenticated()")
	@PostMapping (value = "/{bno}/new" ,
			      produces = "text/plain; charset=utf-8",
			      consumes = "application/json; charset=utf-8")
	public ResponseEntity<String> registerReplyForBoard (@PathVariable("bno") Long bno, 
			                                             @RequestBody MyReplyVO myReply) {
		
		Long registeredRno = myReplyService.registerReplyForBoard(myReply);
		
		return registeredRno != null ? new ResponseEntity<String>("RegisterSuccess", HttpStatus.OK) 
				                     : new ResponseEntity<String>("RegisterFail", HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	
	@PreAuthorize("isAuthenticated()")
	//댓글에 대한 답글 등록(rno 반환):  	POST /replies/{bno}/{prno}/new
	@PostMapping (value = "/{bno}/{prno}/new" ,
				  produces = "text/plain; charset=utf-8",
				  consumes = "application/json; charset=utf-8")
	public ResponseEntity<String> registerReplyForReply (@PathVariable("bno") Long bno, 
														 @PathVariable("prno") Long prno, 
		                                                 @RequestBody MyReplyVO myReply) {
	
		Long registeredRno = myReplyService.registerReplyForReply(myReply);
		
		return registeredRno != null ? new ResponseEntity<String>("RegisterSuccess", HttpStatus.OK) 
				                     : new ResponseEntity<String>("RegisterFail", HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	//게시물에 대한 특정 댓글 조회: 				GET /replies/{bno}/{rno}
	
	@GetMapping(value = "/{bno}/{rno}",
			    produces = "application/json;charset=utf-8")
	public ResponseEntity<MyReplyVO> showReply(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno){
		
		MyReplyVO myReply = myReplyService.getReply(bno, rno) ;
		
		return new ResponseEntity<MyReplyVO>(myReply, HttpStatus.OK) ;
	}
	
	//게시물에 대한 특정 댓글 수정: 				PUT:PATCH /replies/{bno}/{rno}
	@PreAuthorize("isAuthenticated() && principal.username == #myReply.rwriter")
	@RequestMapping(value = "/{bno}/{rno}" , 
			        method = {RequestMethod.PATCH , RequestMethod.PUT},
	                consumes = "application/json;charset=utf-8",
	                produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> modifyReply(@PathVariable("bno") Long bno, 
											  @PathVariable("rno") Long rno,
											  @RequestBody  MyReplyVO myReply){
		//myReply.setBno(bno) ;  //Setter없음
		//myReply.setRno(rno) ;  //Setter없음
		int modCnt = myReplyService.modifyReply(myReply) ;
		
		return modCnt == 1 ? new ResponseEntity<String>("ModifySuccess", HttpStatus.OK)
				           : new ResponseEntity<String>("ModifyFail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//@PreAuthorize("isAuthenticated() && principal.username == #rwriter")
	@PreAuthorize("isAuthenticated() && principal.username == #myReply.rwriter")
	//게시물에 대한 특정 댓글 삭제: 				DELETE: /replies/{bno}/{rno}
	@DeleteMapping(value = "/{bno}/{rno}", produces = { "text/plain; charset=UTF-8" })
	public ResponseEntity<String> removeReply(@PathVariable("bno") Long bno,
											  @PathVariable("rno") Long rno,
											  @RequestBody MyReplyVO myReply) { //VO객체를 명시해야 값이 전달됨 
											  //@RequestBody String rwriter){   //값 전달이 않됨

		int delCnt = myReplyService.removeReply(bno, rno);

		return delCnt == 1
						  ? new ResponseEntity<>("DeleteSuccess", HttpStatus.OK)
						  : new ResponseEntity<>("DeleteFail", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
