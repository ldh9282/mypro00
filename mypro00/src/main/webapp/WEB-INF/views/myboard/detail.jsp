<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="<%=request.getContextPath() %>" />

<%@ include file="../myinclude/myheader.jsp" %>



<style>
.txtBoxCmt, .txtBoxComment { overflow: hidden ; resize: vertical; min-height: 100px ; color: black ; }
</style>


<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header" style="white-space: nowrap;" 
               >Board - Detail: <c:out value="${board.bno }"/> 번 게시물</h3>
        </div><%-- /.col-lg-12 --%>
    </div><%-- /.row --%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                
<div class="row">
	<div class="col-md-3" style="white-space: nowrap; height: 45px; padding-top:11px;">
		<strong style="font-size:18px;">${board.bwriter}님 작성</strong>
	</div>
	<div class="col-md-3" style="white-space: nowrap; height: 45px; padding-top:16px;">
		<span class="text-primary" style="font-size: smaller; height: 45px; padding-top: 19px;">
			<span>
				<span>등록일:&nbsp;</span>
				<strong><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${board.bregDate}"/></strong>
				<span>&nbsp;&nbsp;</span>
			</span>
			<span>조회수:&nbsp;<strong><c:out value="${board.bviewCnt}"/></strong></span>
		</span>
	</div>
	<div class="col-md-6" style="height: 45px; padding-top:6px;"><%-- vertical-align: middle; --%>
		<div class="button-group pull-right"><%-- 
		
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal" var="principal"/>
				<c:if test="${principal.username eq board.bwriter }"> --%>
		
			<button type="button" id="btnToModify" data-oper="modify"
					class="btn btn-primary"><span>수정</span></button><%-- 
				</c:if>
		</sec:authorize> --%>
		
		
			<button type="button" id="btnToList" data-oper="list"
					class="btn btn-info"><span>목록</span></button>
		</div>
	</div>
</div>

                </div><%-- .panel-heading 끝 --%>
                
                
                
                <div class="panel-body form-horizontal">

	<div class="form-group">
	    <label class="col-sm-2 control-label" style="white-space: nowrap;">글제목</label>
	    <div class="col-sm-10">
		    <input class="form-control" name="btitle" value='<c:out value="${board.btitle }"/>'
		                               readonly="readonly" />
		</div> 
	</div>


	<div class="form-group">
		<label class="col-sm-2 control-label" style="white-space: nowrap;">글내용</label>	<%-- <textarea>와 </textarea>는 사이에 공백이 없어야
																							 데이터베이스 저장 시에 필요 없는 공백이 포함되지 않음 --%>
		<div class="col-sm-10">						  
			<textarea class="form-control" rows="3" name="bcontent" style="resize:none;"
					  readonly="readonly"><c:out value="${board.bcontent}"/></textarea>
		</div>
	</div>

	
	<div class="form-group">
		<label class="col-sm-2 control-label" style="white-space: nowrap;">최종수정일</label>
		<div class="col-sm-10">	
			<input class="form-control" name="bmodDate"
				   value='<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${board.bmodDate}"/>'
				   readonly="readonly" />
		</div>
	</div>
	


<%-- Modal 시작 --%>
<div class="modal fade" id="yourModal" tabindex="-1" role="dialog" aria-labelledby="yourModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="yourModalLabel">처리결과</h4>
            </div>
            <div class="modal-body">처리가 완료되었습니다.</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">확인</button>
            </div>
        </div><%-- /.modal-content --%>
    </div><%-- /.modal-dialog --%>
</div><%-- /.modal 끝 --%>


<form id="frmSendValue">
	<input type="hidden" name="bno" id="bno" value='<c:out value="${board.bno }"/>' />
	<input type="hidden" name="pageNum" value="${myBoardPagingDTO.pageNum }"/>
	<input type="hidden" name="rowAmountPerPage" value="${myBoardPagingDTO.rowAmountPerPage }"/>
	<input type="hidden" name="scope" value="${myBoardPagingDTO.scope }"/>
	<input type="hidden" name="keyword" value="${myBoardPagingDTO.keyword }"/>
</form>

<%--  첨부파일 표시 --%>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">첨부 파일</div>
			<div class="panel-body">
				<div class="form-group uploadDiv">
					<input id="inputFile" type="file" name="uploadFiles" multiple><br>
				</div>
				<div class="form-group fileUploadResult">
					<ul>
					<%-- 업로드 후, 업로드 처리결과가 표시될 영역 --%>
					</ul>
				</div>
			</div><%-- /.panel-body --%>
		</div><%-- /.panel --%>
	</div><%-- /.col-lg-12 --%>
</div><%-- /.row --%> 


<%-- Modal 시작 --%>
<div class="modal fade" id="attachModal" tabindex="-1" role="dialog" aria-labelledby="attachModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body attachModalDiv">
            	<!-- 이미지 표시 -->
            </div>
        </div><%-- /.modal-content --%>
    </div><%-- /.modal-dialog --%>
</div><%-- /.modal 끝 --%>



                </div><%-- /.panel-body --%>
            </div><%-- /.panel --%>
        </div><%-- /.col-lg-12 --%>
    </div><%-- 게시물 상태 표시 끝 /.row --%>









<%-- 댓글 화면 표시 시작 --%>
    
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default" >
			<div class="panel-heading">
				<p style="margin-bottom: 0px; font-size: 16px;">
					<strong style="padding-top: 2px;"><%--  
						<span>댓글&nbsp;<c:out value="${board.breplyCnt}"/>개</span> --%>
						<span id="replyTotal"></span>
						<span>&nbsp;</span>
						
						<sec:authorize access="isAuthenticated()">
						
						<button type="button" id="btnChgCmtReg" class="btn btn-info btn-sm">댓글 작성</button>
						<button type="button" id="btnRegCmt" class="btn btn-warning btn-sm"
								style="display:none">댓글 등록</button>
						<button type="button" id="btnCancelRegCmt" class="btn btn-warning btn-sm"
								style="display:none">취소</button>
						</sec:authorize>
					</strong>
				</p>
			</div> <%-- /.panel-heading --%>
			<div class="panel-body">
				
			<sec:authorize access="isAuthenticated()">
				
				<%-- 댓글 입력창 div 시작 --%>
				<div class="form-group" style="margin-bottom: 5px;">
					<textarea class="form-control txtBoxCmt" name="rcontent"
							  placeholder="댓글작성을 원하시면,&#10;댓글 작성 버튼을 클릭해주세요."
							  readonly="readonly"
							></textarea>
				</div>
				<hr style="margin-top: 10px; margin-bottom: 10px;">
				
				
			</sec:authorize>
				
				
				
				<%-- 댓글 입력창 div 끝 --%>
				<ul class="chat">
					<%-- 댓글 목록 표시 영역 - JavaScript로 내용이 생성되어 표시됩니다.--%>
					
					
				</ul><%-- /.chat --%>
			</div><%-- /.panel-body --%>
			<div class="panel-footer text-center" id="showCmtPagingNums">
				<%-- 댓글 목록의 페이징 번호 표시 영역 - JavaScript로 내용이 생성되어 표시됩니다.--%>
			</div>
		</div><%-- /.panel --%>
	</div><%-- .col-lg-12 --%>
</div><%-- .row : 댓글 화면 표시 끝 --%>


<%-- 댓글 페이징 데이터 저장 form --%>
<form id="frmCmtPagingValue">
	<input type='hidden' name='pageNum' value='' />
	<input type='hidden' name='rowAmountPerPage' value='' />
</form>
    
    
    
</div><%-- /#page-wrapper --%>


<script type="text/javascript" src="${contextPath }/resources/js/mycomment.js"></script>

<script type="text/javascript">

var frmSendValue = $("#frmSendValue") ;

var bwriter = '<c:out value="${board.bwriter }"/>' ;

var loginUser = "" ;

<sec:authorize access="isAuthenticated()">
	loginUser = '<sec:authentication property="principal.username"/>' ;
</sec:authorize>

//게시물 수정 페이지로 이동
$("#btnToModify").on("click", function(){
	
	if(!loginUser || loginUser != bwriter){
		alert("글 작성자만 수정페이지로 이동할 수 있습니다") ;
		return ;
	}
		
	//location.href='${contextPath}/myboard/modify?bno=<c:out value="${board.bno}"/>';
	frmSendValue.attr("action", "${contextPath}/myboard/modify");
	frmSendValue.attr("method", "get") ;
	frmSendValue.submit() ;
}) ;


//게시물 목록 페이지로 이동
$("#btnToList").on("click", function(){
	//location.href="${contextPath}/myboard/list";
	frmSendValue.find("#bno").remove() ;
	//frmSendValue.find("input[name='bno']").remove() ;
	frmSendValue.attr("action", "${contextPath}/myboard/list");
	frmSendValue.attr("method", "get") ;
	frmSendValue.submit() ;
	
})

var result = '<c:out value="${result}"/>';

function checkModifyOperation(result) {
	if (result === ''|| history.state) {
		return;
	} else if (result === 'successModify'){
		var myMsg = "글이 수정되었습니다";
	}
	
	//alert(myMsg);
	$(".modal-body").html(myMsg) ;
	$("#yourModal").modal("show");
	myMsg='';
}


//댓글 답글 처리 자바스크립트 시작

<%-- 웹 브라우저에 표시된 HTML 내용에서 일어나는 모든 Ajax 전송 요청에 대하여 csrf 토큰값이 요청 헤더에 설정됨 --%>
var csrfHeaderName = "${_csrf.headerName}" ;
var csrfTokenValue = "${_csrf.token}" ;

$(document).ajaxSend(function(e, xhr, options){
	xhr.setRequestHeader(csrfHeaderName, csrfTokenValue) ;
	
});


//댓글에 대한 게시물 번호(bno)
var bnoValue = '<c:out value="${board.bno}"/>' ;
/* 
var loginUser = "" ;

<sec:authorize access="isAuthenticated()">
	loginUser = '<sec:authentication property="principal.username"/>' ;
</sec:authorize> */



//댓글 목록 표시 함수
var commentUL = $(".chat") ;
var frmCmtPagingValue = $("#frmCmtPagingValue") ;

function showCmtList(page){
		
	myCommentClsr.getCmtList(
		{bno: bnoValue, page: page || 1 } ,
		function (replyPagingCreator) {
			//console.log("서버로부터 전달된 pageNum(replyPagingCreator.myRelyPaging.pageNum): "
			//			+ replyPagingCreator.myReplyPagingDTO.pageNum);
			
			
			$("#replyTotal").html("댓글:&nbsp;" + replyPagingCreator.rowAmountTotal + "개");
			
			
			frmCmtPagingValue.find("input[name='pageNum']").val(replyPagingCreator.myReplyPagingDTO.pageNum);
			
			//console.log("폼에 저장된 페이징번호 pageNum(): "
			//			+ frmCmtPagingValue.find("input[name='pageNum']").val());
			
			var str = "" ;
			
			//댓글이 없으면, if 문의 블록이 실행되고, 함수 실행이 중지됨(return), 따라서, for문 실행 않됨
			if( !replyPagingCreator.replyList || replyPagingCreator.replyList.length == 0 ) {
				//alert(typeof replyPagingCreator.replyList) ;
				
				str += '<li class="left clearfix commentLi" '
				    +  '    style="text-align: center; background-color: lightCyan; height: 35px; margin:bottom: 0px; padding-bottom; 0px; padding-top: 6px;border: 1px dotted;">' 
				    +  '  <strong>등록된 댓글이 없습니다.</strong></li>' ;
				
				commentUL.html(str);
				return ;
 				
			}
			
			for(var i = 0, len = replyPagingCreator.replyList.length; i < len ; i++){
				str += '<li class="left clearfix commentLi" data-bno="' + bnoValue + '"' 
				    + '    data-rno="' + replyPagingCreator.replyList[i].rno +'">'
				    + '   <div>' ;
				
				//댓글에 대한 답글 들여쓰기
			if(replyPagingCreator.replyList[i].lvl == 1){
				str += '       <div>' ;
				
			} else if (replyPagingCreator.replyList[i].lvl == 2) {
				str += '       <div style="padding-left: 25px;">' ;
			} else if (replyPagingCreator.replyList[i].lvl == 3) {
				str += '       <div style="padding-left: 50px;">' ;
			} else if (replyPagingCreator.replyList[i].lvl == 4) {
				str += '       <div style="padding-left: 75px;">' ;
			} else {
				str += '       <div style="padding-left: 100px;">' ;
			}
				    
				str +='        <span class="header info-rwriter">'
					+ '             <strong class="primary-font">';
					
			if(replyPagingCreator.replyList[i].lvl > 1) {
				str +='             <i class="fa fa-reply fa-fw"></i>&nbsp;' ;
			}
					
					
				str	+= replyPagingCreator.replyList[i].rwriter + '</strong>'
					+ '             <span>&nbsp;</span>'
					+ '             <small class="text-muted">수정일: ' 
					//+               replyPagingCreator.replyList[i].rmodDate
					+               myCommentClsr.showDatetime(replyPagingCreator.replyList[i].rmodDate)
					+ '             </small>'
					+ '        </span>' 
					+ '		   <p data-bno="' + replyPagingCreator.replyList[i].bno + '"' 
					+ '           data-rno="' + replyPagingCreator.replyList[i].rno + '"'
					+ '           data-rwriter="' + replyPagingCreator.replyList[i].rwriter + '"' 
					+ '           >' + replyPagingCreator.replyList[i].rcontent + '</p>'
				    + '       </div>' ;
				    
				    
				    
			<sec:authorize access="isAuthenticated()">
			
				str +='       <div class="btnsReply" style="margin-bottom:10px">'
				    + '           <button type="button" style="display:in-block"'
					+ '                   class="btn btn-primary btn-xs btnChgReplyReg"><span>답글 작성</span></button>'
				    + '       </div>' ;
			</sec:authorize>
				    
				    
				str +='    </div>'
					+ '</li>' ;
				
				
					
			}  //for-end
			
			commentUL.html(str); //html() 사용 시, 새로운 내용으로 교체.
			//commentUL.append(str);  //append 사용 시, 기존 내용 밑에 새로운 내용 추가 (페이징 대신 사용).
			
			showCmtPagingNums(replyPagingCreator.myReplyPagingDTO.pageNum ,
					          replyPagingCreator.startPagingNum , 
					          replyPagingCreator.endPagingNum ,
					          replyPagingCreator.prev ,
					          replyPagingCreator.next ,
					          replyPagingCreator.lastPageNum) ;
			
			
		} //callback-함수 end
	
	); //매개변수 선언 end

} //showCmtList 함수 end 




//댓글 목록에 표시할 페이징번호 생성 함수: ReplyPagingCreator로 부터 받아온 값들을 이용
function showCmtPagingNums(pageNum, startPagingNum, endPagingNum, prev, next, lastPageNum) {
	if (endPagingNum >= lastPageNum) {
		endPagingNum = lastPageNum ;
	}
	
	var str  = '<ul class="pagination pagination-sm" >' ;
	//맨 앞으로
	if(prev) {
		//맨 앞으로
		str += '    <li class="page-item" >'
		    +  '         <a class="page-link" href="1" >'
		    +  '             <span aria-hidden="true" >&laquo;</span>'
		    +  '         </a>'
		    +  '    </li>' ;
		//이전 페이지    
		str += '    <li class="page-item" >'
		    +  '         <a class="page-link" href="' + (startPagingNum - 1) + '" >'
		    +  '             <span aria-hidden="true" >이전</span>'
		    +  '         </a>'
		    +  '    </li>' ;
	}
		    
		//선택된 페이지 번호 Bootstrap의 색상표시
	for(var i = startPagingNum ; i <= endPagingNum ; i++){
		//active는 Bootstrap 클래스 이름
		var active = ((pageNum == i) ? "active" : "" );
			
		str +='     <li class="page-item ' + active + '">'
		    + '         <a class="page-link" href="' + i + '" >'
	        + '             <span aria-hidden="true" >' + i + '</span>'
	        + '         </a>'
	        + '     </li>' ;
	}
		
	if(next) {
		//다음 페이지
		str += '    <li class="page-item" >'
		    +  '         <a class="page-link" href="' + (endPagingNum + 1) + '" >'
		    +  '             <span aria-hidden="true" >다음</span>'
		    +  '         </a>'
		    +  '    </li>' ;
		//맨마지막으로     
		str += '    <li class="page-item" >'
		    +  '         <a class="page-link" href="' + lastPageNum + '" >'
		    +  '             <span aria-hidden="true" >&raquo;</span>'
		    +  '         </a>'
		    +  '    </li>' ;

		    
		str += '</ul>' ;
		
	}
	
	$("#showCmtPagingNums").html(str);
	
}
	
//선택된 페이징 번호의 게시물목록 가져오는 함수: 이벤트 전파 이용
$("#showCmtPagingNums").on("click", "ul li a" ,function(e){
	e.preventDefault() ;
	
	var targetPageNum = $(this).attr("href")  ;
	console.log("targetPageNum: " + targetPageNum);
	
	showCmtList(targetPageNum) ;
});


<%--댓글 작성 버튼 클릭 - 댓글 등록 버튼으로 변경, 댓글 입력창 활성화--%>
$("#btnChgCmtReg").on("click", function(){
	
	chgBeforeReplyBtn() ;
	chgBeforeCmtRepBtns();
	
	$(this).attr("style", "display:none") ;
	$("#btnRegCmt").attr("style" , "display:in-block; margin-right:2px");
	$("#btnCancelRegCmt").attr("style" , "display:in-block");
	$(".txtBoxCmt").attr("readonly", false) ;
	
});

<%--댓글 추가 요소 초기화 함수 --%>
function chgBeforeCmtBtn() {
	$("#btnChgCmtReg").attr("style", "display:in-block") ;
	$("#btnRegCmt").attr("style" , "display:none");
	$("#btnCancelRegCmt").attr("style" , "display:none");
	$(".txtBoxCmt").val("");
	$(".txtBoxCmt").attr("readonly", true) ;
	
}


<%--댓글등록 취소 클릭 --%>
$("#btnCancelRegCmt").on("click", function(){
	
	if(!confirm("댓글 입력을 취소하시겠습니까?")) {
		return ;
	}
	
	chgBeforeCmtBtn() ;
	
});

<%--댓글등록 버튼 클릭 이벤트 처리 --%>
$("#btnRegCmt").on("click", function(){
	
	if(!loginUser){
		alert("로그인 후, 댓글 등록이 가능합니다.") ;
		return ;
	}
	
	
	//var loginUser = "user9" ;
	var txtBoxCmt = $(".txtBoxCmt").val() ;
	
	var comment = {bno: bnoValue, rcontent: txtBoxCmt, rwriter: loginUser} ;
	
	console.log("댓글등록: 서버전송 객체내용: " + comment);
	
	myCommentClsr.registerCmt(
		comment,
		function(serverResult) {
			
			if(serverResult == "RegisterSuccess"){
				alert("댓글이 등록되었습니다.") ;
			}
			
			chgBeforeCmtBtn() ;
			
			showCmtList(-10) ;
			
			
		}
		
	)
	
}) ;


<%--답글 작성 버튼 클릭 이벤트: 이벤트 전파이용 --%>

$(".chat").on("click", "li div div .btnChgReplyReg", function(){
	$("p").attr("style", "display:in-block;") ;
	
	chgBeforeReplyBtn() ;
	chgBeforeCmtBtn() ;
	chgBeforeCmtRepBtns();
	
	var strTxtBoxReply =
		  "<textarea class='form-control txtBoxReply' name='rcontent' style='margin-bottom:10px;'"
		+ "        placeholder='답글작성을 원하시면, &#10;답글 작성 버튼을 클릭해주세요.'"
		+ "       ></textarea>"
		+ "<button type='button' class='btn btn-warning btn-xs btnRegReply'>답글 등록</button>"
		+ "<button type='button' class='btn btn-danger btn-xs btnCancelRegReply'"
		+ " style='margin-left:4px;'>취소</button>";
	
	$(this).after(strTxtBoxReply) ;
	$(this).attr("style", "display: none")
	
});

<%--답글 관련 화면 상태 초기화--%>
function chgBeforeReplyBtn(){
	$(".btnRegReply").remove() ;
	$(".btnCancelRegReply").remove() ;
	$(".txtBoxReply").remove() ;
	$(".btnChgReplyReg").attr("style", "display:in-block") ;
	
}

<%--답글등록 취소 클릭--%>
$(".chat").on("click", ".commentLi .btnCancelRegReply", function(){
	
	if(!confirm("답글 입력을 취소하시겠습니까?")) {
		return ;
	}
	
	chgBeforeReplyBtn() ;
	
});

<%--답글 등록 버튼 클릭 이벤트 처리: 답글이 달린 댓글이 있는 페이지 표시--%>
$(".chat").on("click", ".commentLi .btnRegReply", function(){
	
	//var loginUser = "test8" ;
	
	if(!loginUser) {
		alert("로그인 후에만 글 작성이 가능합니다.") ;
		return ;
	}
	
	
	var pageNum = frmCmtPagingValue.find("input[name='pageNum']").val() ;
	console.log("답글 추가가 발생된 댓글 페이지 번호: "+ pageNum);
	
	var txtBoxReply = $(this).prev().val() ;
	
	var prnoVal = $(this).closest("li").data("rno") ;
	console.log("prnoVal: " + prnoVal) ;
	
	var reply = { bno: bnoValue, 
				  rcontent: txtBoxReply,
				  rwriter: loginUser, 
				  prno: prnoVal } ;
	
	console.log("답글등록: 서버전송 객체내용: " + reply);
	
	myCommentClsr.registerReply(reply,
		function(serverResult){
		
			if(serverResult == "RegisterSuccess"){
				alert("답글이 등록되었습니다.") ;
			}
			
			chgBeforeReplyBtn() ;
			
			showCmtList(pageNum) ;
			
	});
	
});

<%--댓글/답글 수정 입력창 초기화 함수--%>
function chgBeforeCmtRepBtns(){
	
	$("p").attr("style", "display:in-block;") ;
	
	$(".btnModCmt").remove() ;
	$(".btnDelCmt").remove() ;
	$(".btnCancelCmt").remove() ;
	$(".txtBoxMod").remove() ;
	
}



<%--댓글-답글 수정/삭제 화면 요소 표시: p 태그 클릭 이벤트 --%>
$(".chat").on("click", "li p", function(){
	
	chgBeforeCmtBtn();<%--댓글 등록 상태 초기화--%>
	chgBeforeReplyBtn()<%--다른 답글 등록 상태 초기화--%>
	chgBeforeCmtRepBtns(); <%--다른 답글/댓글 수정 상태 초기화--%>
	
	if(!loginUser) {
		alert("로그인 후, 수정이 가능합니다.") ;
		return ;
	}
	
	var rwriter = $(this).data("rwriter") ;
	
	if(loginUser != rwriter) {
		alert("작성자만 수정이 가능합니다.") ;
		return ;	
	}
	
	
	$(this).parents("li").find(".btnChgReplyReg").attr("style","display: none");
	
	var rcontent = $(this).text() ;
	console.log("선택된 댓글내용: " + rcontent);
	
	var strTxtBoxReply =
		  "<textarea class='form-control txtBoxMod' name='rcontent' style='margin-bottom:10px;'"
		+ " placeholder='답글작성을 원하시면,&#10;답글 작성 버튼을 클릭해주세요.'"
		+ "></textarea>"
		+ "<button type='button' class='btn btn-warning btn-sm btnModCmt'>수정</button> "
		+ "<button type='button' class='btn btn-danger btn-sm btnDelCmt'>삭제</button>"
		+ "<button type='button' class='btn btn-info btn-sm btnCancelCmt' style='margin-left: 4px;'>취소</button>";
	
	$(this).after(strTxtBoxReply) ;
	$(".txtBoxMod").val(rcontent) ;
	$(this).attr("style", "display: none;") ;
		
}) ;


<%--댓글답글 수정의 취소 버튼 클릭 이벤트 --%>
$(".chat").on("click", "li .btnCancelCmt",function(){
	chgBeforeCmtRepBtns() ;
	chgBeforeReplyBtn()
});

<%-- 댓글-답글 수정 처리: 수정 버튼 클릭 이벤트 --%>
$(".chat").on("click", "li .btnModCmt",function(){
	
	if(!loginUser){
		alert("로그인하세요!!!") ;
		return ;
	}
	
	
	var rwriterVal = $(this).siblings("p").data("rwriter") ;
	
	if (loginUser != rwriterVal){
		alert("작성자가 아니군요.") ;
		return ;
	}
	
	
	var pageNum = frmCmtPagingValue.find("input[name='pageNum']").val() ;
	
	var txtBoxComment = $(this).prev().val() ;
	
	var rnoVal = $(this).closest("li").data("rno") ;
	
	var comment = {bno: bnoValue ,
			       rno: rnoVal ,
			       rcontent: txtBoxComment , 
			       rwriter: rwriterVal } ;
	
	myCommentClsr.modifyCmtReply(
		comment,
		function(serverResult){
			alert("수정되었습니다.") ;
			
			showCmtList(pageNum) ;
			
		}
	
	);
});



<%-- 댓글-답글 삭제 버튼 클릭 이벤트 --%>
$(".chat").on("click", "li .btnDelCmt",function(){
	
	if(!loginUser){
		alert("로그인하세요!!!") ;
		return ;
	}
	
	var rwriterVal = $(this).siblings("p").data("rwriter") ;
	
	if (loginUser != rwriterVal){
		alert("작성자가 아니군요.") ;
		return ;
	}
	
	var delConfirm = confirm('삭제하시겠습니까?');
	
	if (!delConfirm) {
		alert("삭제가 취소되었습니다.") ;
		return ;
	}
			
	var pageNum = frmCmtPagingValue.find("input[name='pageNum']").val() ;
	
	var rnoVal = $(this).closest("li").data("rno") ;
	
	var comment = {bno: bnoValue ,
			       rno: rnoVal ,
			       rwriter: rwriterVal } ;
	
	myCommentClsr.removeCmtReply(
		comment,
		function(serverResult){
			alert("삭제 되었습니다.") ;
			
			showCmtList(pageNum) ;
			
		}
	
	);
	
		
});	


//첨부파일 처리 시작
//DB 정보를 이용 첨부파일 정보 표시 함수: uploadResult 매개변수에서 DB로 부터 전달된 첨부파일 정보가 저장됨
function showUploadedFiles(uploadResult) {
	
	if(!uploadResult || uploadResult.length == 0) {
		return ;	
	}
	
	var fileUploadResult = $(".fileUploadResult ul") ;
	
	var str = "";
	
	
	$(uploadResult).each(function(i, obj){
	
		
		//calledPathFileName = encodeURI(calledPathFileName.replace(new RegExp(/\\/g), "/")) ;
	
		if (obj.fileType == "F") {
		
			var calledPathFileName =  obj.repoPath + "/" + obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName ;
			//console.log("calledPathFileName: "+ calledPathFileName);
			
			str += "<li data-repopath='" + obj.repoPath + "'" 
			    +  "    data-uploadpath='" + obj.uploadPath + "'" 
			    +  "    data-uuid='" + obj.uuid + "'"
			    +  "    data-filename='" + obj.fileName + "' data-filetype='F'>" 
			    +  "    <img src='${contextPath}/resources/img/icon-attach.png'"
			    +  "         alt='No icon' style='height:18px; width: 18px;' />&nbsp;"
			    +       obj.fileName
			    +  "</li>" ;
			
		} else  {  //if(obj.fileType == "I")

			var thumbnailPath =  obj.repoPath + "/" + obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName ;
		
			
			str += "<li data-repopath='" + obj.repoPath + "'" 
		    	+  "    data-uploadpath='" + obj.uploadPath + "'" 
			    +  "    data-uuid='" + obj.uuid + "'"
			    +  "    data-filename='" + obj.fileName + "' data-filetype='I'>"
			    +  "    <img src='${contextPath}/displayThumbnail?fileName=" + encodeURI(thumbnailPath) + "'"
			    +  "         alt='No Image' style='height:18px; width: 18px;'/>&nbsp;" 
			    +       obj.fileName
			    +  "</li>" ;
		}
		
	});

	fileUploadResult.append(str) ;
}


$(".fileUploadResult ul").on("click", "li", function(){
	var objLi = $(this);
	
	var downloadFileName = objLi.data("repopath") + "/" + objLi.data("uploadpath") + "/" + objLi.data("uuid") + "_" + objLi.data("filename") ;
	downloadFileName = downloadFileName.replace(new RegExp(/\\/g), "/") ;
	
	if(objLi.data("filetype") == "I") {
		$(".attachModalDiv").html(
				"<img src='${contextPath}/fileDownloadAjax?fileName=" + encodeURI(downloadFileName) + "' width='100%' />"
		);
		
		$("#attachModal").modal("show") ;
		
	} else {
		self.location.href = "${contextPath}/fileDownloadAjax?fileName=" + encodeURI(downloadFileName) ;
	}
	
}) ;

//이미지 표시 모달 감추기
$("#attachModal").on("click", function(){
	$("#attachModal").modal("hide") ;
});




$(document).ready(function(){
	
	$.ajax({
		type: "get" ,
		url: "${contextPath}/myboard/getFiles" ,
		data: {bno: bnoValue} ,
		dataType: "json" ,
		success: function(fileList, status) {
			showUploadedFiles(fileList) ;
		}
	});
	
	
	//모달 동작 후, 아래의 history.pushState() 동작과 popstate 이벤트 리스너에 의해 뒤로가기 방지됨.
	checkModifyOperation(result);
	//popstate 이벤트를 처리하는 자바스크립트 리스너 추가, popstate는 간단히 브라우저의 뒤로가기 버튼 클릭 이벤트 이름입니다.
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, location.href); //뒤로가기 Block.
	})
	//페이지 로딩 시에, 실행되어 현재 목록페이지의 URL을 강제로 최근 URL로서 히스토리 객체에 다시 추가
	history.pushState(null, null, location.href);
	
	showCmtList(1) ;
	
});




//게시물 번호 저장
//var bnoValue = '<c:out value="${board.bno}"/>';

/*
//댓글 목록 데이터 요청/받기 Ajax 테스트    
myCommentClsr.getCmtList(
{bno:bnoValue, page:1},   //1번째 bnoAndPage의 인자값(JS객체)
function(replyPagingCreator){  //2번째 callback 인자값: 성공 시 실행되는 함수(익명블록)
    for (var i = 0, len = replyPagingCreator.replyList.length || 0 ; i < len; i++){
        console.log(replyPagingCreator.replyList[i]);
    }
}
);
*/
/* 
//댓글등록  테스트 
myCommentClsr.registerCmt(
{bno:bnoValue, rcontent:"JS-클로저-댓글입력테스트입니다.", rwriter: "user7"} , //1번째: comment인자값 
function(result){    //2번째: callback 인자값: 성공 시 실행되는 함수
    alert("myReplyClsr.registerCmt()처리결과 " + result);
}
); */

/* 
//답글등록 테스트 
myCommentClsr.registerReply(
{bno: bnoValue, prno: 1, rcontent: "JS-클로저-댓글의 답글입력테스트입니다.", rwriter: "user10"}, 
function(result){ //2번째 callback 인자값: 성공 시 실행되는 익명블록 형태의 콜백함수
    alert("myReplyClsr.registerReply()처리결과 " + result); 
}
); */


/* 
//댓글-답글 조회 테스트 
myCommentClsr.getCmtReply(
{bno: bnoValue, rno: 1 },
function(data){
    console.log(data);
}
);  */

/* 
//댓글-답글 수정 테스트 
myCommentClsr.modifyCmtReply(
{bno : bnoValue, rno : 1, rcontent: "JS클로저에 의한 댓글 수정======="}   ,
function(modifyResult) {
    alert(modifyResult + "- ajax 처리 완료");
}
);  */

/*   
//댓글-답글 삭제 테스트(실제 삭제 발생) 
myCommentClsr.removeCmtReply(
{bno : bnoValue, rno : 62, rwriter: "user10"}, //댓글-답글 입력 테스트 시, SQL*Developer 에서 확인한 rno를 사용
function(deleteResult) {
    console.log(deleteResult);
    if (deleteResult === "댓글 삭제 성공") {
        alert(deleteResult + ": 댓글/답글이 삭제되었습니다(ByRemoveCmtReply).");
        }
},
function(err) {
    alert("오류로 인한 댓글/답글 삭제 작업 취소...");
  }
); */








</script>




        
<%@ include file="../myinclude/myfooter.jsp" %>
        
        
