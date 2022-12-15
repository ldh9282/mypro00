<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>


<%-- common/myLogout.jsp --%>
<%-- MyLoginLogoutController 의 logoutPageGET 메서드에 의한 Requset 받음 --%>
<%-- http://localhost:8080/mypro00/logout 에 매핑된 스프링 시큐리티에 Response 보냄 --%>    
<%-- 스프링 시큐리티 구성정보는 WEB/INFspring/security-context.xml 에 저장되어있음 --%>    
<!DOCTYPE html>
<html lang="ko">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Logout</title>

    <!-- Bootstrap Core CSS -->
    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${contextPath}/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${contextPath}/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="${contextPath}/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default" style="margin-top: 10%;">
                   <div class="panel-heading">
                       <h2 class="panel-title">Really Logout ?</h2>
                   </div>
                   <div class="panel-body">
                       <form role="form" method="post" action="${contextPath }/logout">
                       
                           <fieldset>
                               <div>
                                   <button type="submit" class="btn btn-lg btn-success btn-block">Log
                                       out</button>
                               </div>
                           </fieldset>
                           
                           <sec:csrfInput/>
                       </form>
									</div>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${contextPath}/resources/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="${contextPath}/resources/dist/js/sb-admin-2.js"></script>



<script type="text/javascript">

var frmMyLogout = $("#frmMyLogout");

$("#btnMyLogout").on("click", function() {
	var logoutDecision = confirm("로그아웃 하시겠습니까?");
	
	if (!logoutDecision) {
		alert("로그아웃이 취소하겠습니다");
		return;
	} else {
		alert("로그아웃 되었습니다")
		frmMyLogout.submit();
	}
}

</script>


</body>

</html>
    