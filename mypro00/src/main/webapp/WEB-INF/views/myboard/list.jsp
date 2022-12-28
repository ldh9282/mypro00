<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="<%=request.getContextPath() %>" />

<%@ include file="../myinclude/myheader.jsp" %>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header">Board - List</h3>
        </div><%-- /.col-lg-12 --%>
    </div><%-- /.row --%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"><!-- 
                    <h4>게시물 목록</h4> -->
                    <div class="row">
						<div class="col-md-6" style="font-size:20px; height: 45px; padding-top:10px;">게시글 목록</div>
						<div class="col-md-6" style="padding-top:8px;">
							<button type="button" id="btnMoveRegister" class="btn btn-primary btn-sm pull-right">새글 등록</button>
						</div>
					</div>
                </div><%-- /.panel-heading --%>
                <div class="panel-body">

<form class="form-inline" id="frmSendValue" action="${contextPath }/myboard/list" method="get" name="frmSendValue">

<div class="form-group">
    <label class="sr-only">frmSendValue</label>
    <select class="form-control" id="selectAmount" name="rowAmountPerPage">
        <option value="10" <c:out value="${pagingCreator.myBoardPagingDTO.rowAmountPerPage == '10' ? 'selected' : '' }" />     
               	>10개</option>
        <option value="20" <c:out value="${pagingCreator.myBoardPagingDTO.rowAmountPerPage eq '20' ? 'selected' : '' }" />     
               	>20개</option>
        <option value="50" <c:out value="${pagingCreator.myBoardPagingDTO.rowAmountPerPage == '50' ? 'selected' : '' }" />     
        		>50개</option>
        <option value="100" <c:out value="${pagingCreator.myBoardPagingDTO.rowAmountPerPage == '100' ? 'selected' : '' }" />     
               >100개</option>       
    </select>
    
    <select class="form-control" id="selectScope" name="scope">
        <option value="" <c:out value="${pagingCreator.myBoardPagingDTO.scope == null ? 'selected' : '' }" />     
               	>검색범위</option>
        <option value="T" <c:out value="${pagingCreator.myBoardPagingDTO.scope == 'T' ? 'selected' : '' }" />     
               	>제목</option>
        <option value="C" <c:out value="${pagingCreator.myBoardPagingDTO.scope == 'C' ? 'selected' : '' }" />     
               	>내용</option>      
        <option value="W" <c:out value="${pagingCreator.myBoardPagingDTO.scope == 'W' ? 'selected' : '' }" />     
               	>작성자</option>    
        <option value="TC" <c:out value="${pagingCreator.myBoardPagingDTO.scope == 'TC' ? 'selected' : '' }" />     
               	>제목+내용</option>    
        <option value="TW" <c:out value="${pagingCreator.myBoardPagingDTO.scope == 'TW' ? 'selected' : '' }" />     
               	>제목+작성자</option>    
        <option value="TCW" <c:out value="${pagingCreator.myBoardPagingDTO.scope == 'TCW' ? 'selected' : '' }" />     
               	>제목+내용+작성자</option>    
    </select>
    
    <div class="input-group">
		<input type="text" class="form-control" id="inputKeyword" name="keyword" placeholder="검색어를 입력하세요" 
		       value='<c:out value="${pagingCreator.myBoardPagingDTO.keyword }" />' />
		<span class="input-group-btn">
		    <button class="btn btn-info" type="button" id="btnSearchGo">
		        검색&nbsp;<i class="fa fa-search"></i>
		    </button>
		</span>
	</div>
    
    <div class="input-group">
    <button class="btn btn-warning" type="button" id="btnReset">검색초기화</button>
    
    </div>
    
</div>

	<input type="hidden" name="pageNum" id="pageNum" value="${pagingCreator.myBoardPagingDTO.pageNum }"/>
	<input type="hidden" name="rowAmountPerPage" id="rowAmountPerPage"  value="${pagingCreator.myBoardPagingDTO.rowAmountPerPage }"/>
	<input type="hidden" name="lastPageNum" id="lastPageNum" value="${pagingCreator.lastPageNum }"/>
</form>
<br>

                
<table class="table table-striped table-bordered table-hover" style="width:100%;text-align:center;" >
<thead>
	<tr>
		<th style="text-align:center;">번호</th>
		<th style="text-align:center;">제목</th>
		<th style="text-align:center;">작성자</th>
		<th style="text-align:center;">작성일</th>
		<th style="text-align:center;">수정일</th>
		<th style="text-align:center;">조회수</th>
	</tr>
</thead>
<tbody>
<c:forEach items="${boardList}" var="board"><%-- 컨트롤러에서 보낸 목록객체 이름: boardList --%>
<c:if test="${board.bdelFlag == 1}">
	<tr style="background-color:Moccasin; text-align:center">
		<td><c:out value="${board.bno}" /></td>
		<td colspan="5"><em>작성자에 의하여 삭제된 게시글입니다.</em></td>
	</tr>
</c:if>
<c:if test="${board.bdelFlag == 0}">
 	<tr class="moveDetail" data-bno='<c:out value="${board.bno}"/>' >
		<td><c:out value="${board.bno}" /></td>
		<td style="text-align:left;">
			<c:out value="${board.btitle}"/>
			<small>[댓글수: <strong><c:out value="${board.breplyCnt }"/></strong>]</small>
		
		</td><%-- 
		<td style="text-align:left;" ><a href='${contextPath }/myboard/detail?bno=<c:out value="${board.bno}" />'><c:out value="${board.btitle}"/></a></td> --%>
		<td><c:out value="${board.bwriter}" /></td>
		<td>
		<fmt:formatDate pattern="yyyy/MM/dd" value="${board.bregDate}" /><br><%-- 
		${board.bregDate} --%>
		</td>
		<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${board.bmodDate}" /><br><%-- 
		${board.bmodDate} --%>
		</td>
		<td>${board.bviewCnt}</td>
	</tr>
</c:if>
</c:forEach>
        </tbody>
    </table><%-- /.table-responsive --%>

<%-- Pagination 시작 --%><!-- 
<div class="pull-right"> -->
<div style="text-align:center;">
  <ul class="pagination pagination-sm">
  <c:if test="${pagingCreator.prev }">
    <li class="paginate_button previous">
      <a href="1">&laquo;</a>
    </li>
  </c:if>
  <c:if test="${pagingCreator.prev }">
    <li class="paginate_button previous">
      <a href="${pagingCreator.startPagingNum - 1} ">이전</a>
    </li>
  </c:if>
  <%-- 페이징 그룹의 페이징 숫자(10개 표시) --%>
  <c:forEach var="pageNum" begin="${pagingCreator.startPagingNum }" end="${pagingCreator.endPagingNum }">
  	<li class='paginate_button ${pagingCreator.myBoardPagingDTO.pageNum == pageNum ? "active" : "" }'>
  		<a href="${pageNum}">${pageNum}</a>
  	</li>
  </c:forEach>
  <c:if test="${pagingCreator.next }">
    <li class="paginate_button next">
      <a href="${pagingCreator.endPagingNum + 1} ">다음</a>
    </li>
  </c:if>
  <c:if test="${pagingCreator.next }">
    <li class="paginate_button next">
      <a href="${pagingCreator.lastPageNum} ">&raquo;</a>
    </li>
  </c:if>
  </ul>
</div><%-- Pagination 끝 --%>


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





                    
                    
                    
                </div><%-- /.panel-body --%>
            </div><%-- /.panel --%>
        </div><%-- /.col-lg-12 --%>
    </div><%-- /.row --%>
</div><%-- /#page-wrapper --%>


<script>

$("#btnMoveRegister").on("click", function(){
	//아래에서 self는 window 객체를 의미합니다.
	//self.location.href = "${contextPath}/myboard/register" ;
	location.href = "${contextPath}/myboard/register" ;
	//self.location = "${contextPath}/myboard/register" ;
});


<%--게시물 행(제목) 클릭 이벤트 처리: 게시물 상세 화면 이동//////////////////////////////// --%>
var frmSendValue = $("#frmSendValue");

<%--tr 태그 클릭 시 form의 데이터를 전달하고 detail 화면 요청 --%>
$(".moveDetail").on( "click", function(e) {
	<%-- bno 값이 값이 설정된 hidden 유형의 input 요소를 form에 추가 --%>
	<%-- tr 태그의 data-bno 속성의 값을 data() 함수로 값을 읽어와 value 속성에 지정 --%>
	frmSendValue.append("<input type='hidden' name='bno' value='" + $(this).data("bno") + "'/>");
	frmSendValue.attr("action", "${contextPath}/myboard/detail");<%-- form에 action 속성 지정 --%>
	frmSendValue.attr("method", "get");<%-- form에 method 속성 지정 --%>
	frmSendValue.submit();<%-- form 전송 --%>
});


var result = '<c:out value="${result}" />' ;

function checkModal(result) {
	if (result === ''|| history.state) {
		return;
		
	} else if(result === 'successRemove'){
		var myMsg = "글이 삭제되었습니다";
		
	} else if (parseInt(result) > 0) {
		var myMsg = "게시글 " + parseInt(result) + " 번이 등록되었습니다.";
		
	}
	
	$(".modal-body").html(myMsg) ;
	$("#yourModal").modal("show");
	
	myMsg='';
}
	

//checkModal(result);


$(".paginate_button a").on("click", function(e) {
	e.preventDefault() ;
	
	frmSendValue.find("input[name='pageNum']").val($(this).attr("href")) ; 
	//alert("선택된 페이지: " + frmSendValue.find("input[name='pageNum']").val()) ; 
	frmSendValue.attr("action", "${contextPath}/myboard/list") ;
	frmSendValue.attr("method", "get") ;
	frmSendValue.submit() ;
	
});


<%--표시행수 변경 이벤트 처리--%>
$("#selectAmount").on("change", function(){
	frmSendValue.find("input[name='pageNum']").val(1) ;
	frmSendValue.attr("action","${contextPath}/myboard/list") ;
	frmSendValue.attr("method", "get");
	frmSendValue.submit();
});


<%--검색버튼 클릭 이벤트 처리 --%>
$("#btnSearchGo").on("click", function(e){
	if(!$("#selectScope").find("option:selected").val()) {
		alert("검색 범위를 선택하세요.");
		return false ;
	}
	
	if(      !( (frmSendValue.find("input[name='keyword']").val())  ||
			    (frmSendValue.find("input[name='keyword']").val() !="") )    ){
		alert("검색어를 입력하세요.") ;
		return false ;
	}
	
	frmSendValue.find("input[name='pageNum']").val(1) ;
	
	frmSendValue.submit() ;
	
});


<%--검색초기화 버튼 이벤트처리, 버튼 초기화 시, 1페이지에 목록 정보 다시 표시 --%>
$("#btnReset").on("click", function(){
	
	$("#selectAmount").val(10);
	$("#selectScope").val("");
	$("#inputKeyword").val("");
	$("#pageNum").val(1);
	
	frmSendValue.submit();
	
});





$(document).ready(function(){
	//모달 동작 후, 아래의 history.pushState() 동작과 popstate 이벤트 리스너에 의해 뒤로가기 방지됨.
	checkModal(result);
	//popstate 이벤트를 처리하는 자바스크립트 리스너 추가, popstate는 간단히 브라우저의 뒤로가기 버튼 클릭 이벤트 이름입니다.
	window.addEventListener('popstate', function(event) {
		history.pushState(null, null, location.href); //뒤로가기 Block.
	})
	//페이지 로딩 시에, 실행되어 현재 목록페이지의 URL을 강제로 최근 URL로서 히스토리 객체에 다시 추가
	history.pushState(null, null, location.href);
});



</script>



<%@ include file="../myinclude/myfooter.jsp" %>
        
        
