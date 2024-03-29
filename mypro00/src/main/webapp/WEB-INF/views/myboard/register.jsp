<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="contextPath" value="<%=request.getContextPath() %>" />

<%@ include file="../myinclude/myheader.jsp" %>

<div id="page-wrapper">

    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header">Board - Register</h3>
        </div><%-- /.col-lg-12 --%>
    </div><%-- /.row --%>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"><h4>게시물 등록</h4></div><%-- /.panel-heading --%>
                <div class="panel-body">

<form role="form" name="frmBoard" method="post" action="${contextPath }/myboard/register" >
	<div class="form-group">
	    <label>제목</label><input class="form-control" placeholder="글제목 입력.." name="btitle">
	</div>
	
	<div class="form-group">
	    <label>내용</label><textarea class="form-control" rows="3" name="bcontent"
	                               ></textarea>
	</div>
	
	<div class="form-group">
	    <label>작성자</label>
	    <input class="form-control" name="bwriter" 
	           value='<sec:authentication property="principal.username"/>' readonly="readonly">
	</div><!-- 

    <button type="button" class="btn btn-primary" onclick="sendBoard()" >등록</button> -->
    <button type="button" class="btn btn-primary" id="btnRegister" >등록</button>
    <button type="button" class="btn btn-warning" data-oper="list"
    		onclick="location.href='${contextPath}/myboard/list'">취소</button>
    <sec:csrfInput/><%-- 
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
</form>                
                    
                </div><%-- /.panel-body --%>
            </div><%-- /.panel --%>
        </div><%-- /.col-lg-12 --%>
    </div><%-- /.row --%>


<%-- 첨부파일 표시 영역 --%>   
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">파일첨부</div>
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
    
    
    
    
    
    
</div><%-- /#page-wrapper --%>

<script>

//수정된 게시물 입력값 유무 확인 함수
function sendBoard() {
	var frmBoard=document.frmBoard;

	var btitle=frmBoard.btitle.value;
	var bcontent=frmBoard.bcontent.value;
	var bwriter=frmBoard.bwriter.value;

	if( (btitle.length==0 ||btitle=="") ||
		(bcontent.length==0 ||bcontent=="") ||
		(bwriter.length==0 ||bwriter=="") ){
		
		return false ;
		
	} else {
		//frmBoard.method="post";
		//frmBoard.action="${contextPath}/myboard/register";
		//frmBoard.submit();
		return true ;
	}
}


//input 초기화를 위해 div 요소의 비어있는 input 요소를 복사해서 저장함.

var cloneInputFile = $(".uploadDiv").clone() ;

//ajax를 통한 첨부파일 전송 시 필요한 csrf 토큰값을 변수에 저장
var csrfHeaderName = "${_csrf.headerName}" ;
var csrfTokenValue = "${_csrf.token}" ;

$(".uploadDiv").on("change", "input[type='file']" , function(){
	
	//FormData() Ajax 파일 전송 시에 사용되는 Web API 클래스의 생성자
	var formData = new FormData() ;
	
	//uploadFiles 이름의 file 유형 input 요소를 변수에 저장
	//var inputFiles = $("input[name='myFiles']");
	var inputFiles = $(this);
	
	//inputFiles에 저장된 파일들을 files 변수에 저장.
	//inputFiles[0]은 첫번째 input 요소를 의미(input 요소가 하나만 있더라도 [0]을 명시해야 함).
	var yourFiles = inputFiles[0].files ;
	
	if(yourFiles == null || yourFiles.length == 0){
		return ;
	}
	
	console.log(yourFiles);
	

	
	//formData 객체에 파일추가
	for(var i = 0 ; i < yourFiles.length ; i++){
		
		if(!checkUploadFile(yourFiles[i].name, yourFiles[i].size)) {
			//$(".uploadDiv input[name='myFiles']").val("") ;
			$(".uploadDiv").html(cloneInputFile) ;
			return ;
			
		}
		
		formData.append("uploadFiles", yourFiles[i]) ;
		
	}
	
	//url 키에 명시된 주소의 컨트롤러에게 formData 객체를 POST 방식으로 전송.
	$.ajax({
		type: 'post' ,
		url: '${contextPath}/fileUploadAjaxAction' ,
		data: formData ,
		dataType: "json" ,
		contentType: false , //contentType에 MIME 타입을 지정하지 않음.
		processData: false , //contentType에 설정된 형식으로 data를 처리하지 않음.
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfTokenValue) ;
		} ,
		//success: function(uploadResult, status){
		success: function(listAttachInfo){
			console.log(listAttachInfo) ;
			
			$(".uploadDiv").html(cloneInputFile.html()) ;
			
			showUploadedFiles(listAttachInfo) ;
		}	
			
	});
	
}) ;


//업로드 파일의 확장자 및 최대 파일 크기를 제한하는 함수
function checkUploadFile(fileName, fileSize) {
	
	var maxFileSizeAllowed = 104857600 ;
	var regExpFileExtension = /(.*)\.(exe|sh|zip|alz)$/i ;
	
	//최대 허용 크기 제한 검사
	if(fileSize >= maxFileSizeAllowed) {
		alert("업로드 파일의 제한된 크기(1MB)를 초과했습니다.") ;
		return false ;
		
	}
	
	if(regExpFileExtension.test(fileName)) {
		alert("exe, sh, zip, alz 유형의 파일은 업로드 할 수 없습니다.") ;
		return false ;
		
	}
	
	return true ;
}

//JavaScript 업로드 결과 표시 함수
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
			    +  "    <span data-filename='" + encodeURI(calledPathFileName) + "' data-filetype='F'>[삭제]</span>"
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
			    +  "    <span data-filename='" + encodeURI(thumbnailPath) + "' data-filetype='I'>[삭제]</span>"			    
			    +  "</li>" ;
		}
		
	});

	fileUploadResult.append(str) ;
}



//파일 삭제
$(".fileUploadResult ul").on("click", "li span", function(e){
	var targetFileName = $(this).data("filename") ;
	var targetFileType = $(this).data("filetype") ;
	
	var parentLi = $(this).parent() ;
	//var parentLi = $(this).closest("li") ;
	
	$.ajax({
		type: "post" ,
		url: "${contextPath}/deleteUploadedFile" ,
		data: {fileName: targetFileName, fileType: targetFileType} ,
		dataType: "text" ,
		success: function(result) {
			if (result == "SuccessFileDelete") {
				alert("파일이 삭제되었습니다.") ;
				parentLi.remove() ;
				
			} else {
				alert("오류: 파일 삭제 실패") ;
			}
		}
	
		
	});
	

});

//게시물 등록: 첨부파일 포함
$("#btnRegister").on("click", function(){
	if(!sendBoard()){
		alert("글제목/글내용/작성자를 모두 입력해야 합니다.") ;
		return ;
	}
	
	var formObj = $("form[role='form']") ;
	var strInputHidden = "" ;
	
	//업로드 결과의 li 요소 각각에 대하여 다음을 처리(이벤트 위임 이용)
	$(".fileUploadResult ul li").each(function(i, obj) {
	
		var objLi =$(obj) ;
		
		strInputHidden += "<input type='hidden' name='attachFileList[" + i + "].uuid' value='" + objLi.data("uuid") + "' > "
		               +  "<input type='hidden' name='attachFileList[" + i + "].uploadPath' value='" + objLi.data("uploadpath") + "' > "
		               +  "<input type='hidden' name='attachFileList[" + i + "].fileName' value='" + objLi.data("filename") + "' > "
		               +  "<input type='hidden' name='attachFileList[" + i + "].fileType' value='" + objLi.data("filetype") + "' > "
		               +  "<input type='hidden' name='attachFileList[" + i + "].repoPath' value='" + objLi.data("repopath") + "' > " ;
		

	});
	
	formObj.append(strInputHidden) ;
	formObj.submit() ;
	
});




</script>


<%@ include file="../myinclude/myfooter.jsp" %>
        
        
