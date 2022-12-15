<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="contextPath" value="<%=request.getContextPath() %>" />

<!DOCTYPE html>
<html lang="ko">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>My Admin Board</title>

    <!-- Bootstrap Core CSS -->
    <link href="${contextPath}/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="${contextPath}/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- DataTables CSS --><%-- 
    <link href="${contextPath}/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet"> --%>

    <!-- DataTables Responsive CSS --><%-- 
    <link href="${contextPath}/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet"> --%>

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
    
    <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
    
    <!-- jQuery --><%-- 
    <script src="${contextPath}/resources/vendor/jquery/jquery.min.js"></script> --%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${contextPath}/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="${contextPath}/resources/vendor/metisMenu/metisMenu.min.js"></script>

    <!-- DataTables JavaScript --><%-- 
    <script src="${contextPath}/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="${contextPath}/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="${contextPath}/resources/vendor/datatables-responsive/dataTables.responsive.js"></script> --%>

    <!-- Custom Theme JavaScript -->
    <script src="${contextPath}/resources/dist/js/sb-admin-2.js"></script>

	<style>
		/* a {text-decoration: none;} */
		
		.bigImageWrapper {
			position: absolute;
			display: none;
			justify-content: center;
			align-items: center;
			top:0%;
			width: 100%;
			height: 100%;
			background-color: lightgray;
			z-index: 100;
		}
		.bigImage {
			position: relative;
			display:flex;
			justify-content: center;
			align-items: center;
		}
		.bigImage img { height: 100%; max-width: 100%; width: auto; overflow: hidden }
	
	</style>    

</head>

<body id="me">

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button><%-- 
                <a class="navbar-brand" href="${contextPath }/myLogout">My Admin Board</a> --%><%-- 
                <a class="navbar-brand" href="${contextPath }/logout">My Page</a> --%>
                <a class="navbar-brand" href="${contextPath }/">My Page</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-user fa-fw"></i>
                        	
                        <sec:authorize access="isAuthenticated()">
													<sec:authentication property="principal.username"/><span>님 접속</span>
												</sec:authorize>
												
												<sec:authorize access="isAnonymous()">
													<span>로그인해주세요</span>
												</sec:authorize>
                        <i class="fa fa-caret-down"></i>
                    </a>
										<ul class="dropdown-menu">
											<sec:authorize access="isAuthenticated()">
												<li>
													<a id="myLogout" href="${contextPath}/logout"><i class="fa fa-sign-out fa-fw"></i> Sign out</a>
												</li>
											</sec:authorize>
											
											<sec:authorize access="isAnonymous()">
												<li>
													<a id="myLogin" href="${contextPath}/login"><i class="fa fa-sign-in fa-fw"></i> Sign in</a>
												</li>
											</sec:authorize>
										</ul>
                    
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                    	<li>
                            <a href="${contextPath }/"><i class="fa fa-home fa-fw"></i> Main Page</a>
                        </li>
                        <li>
                            <a href="${contextPath }/myboard/list"><i class="fa fa-table fa-fw"></i> Board List</a>
                        </li>
                        <li>
                            <a href="${contextPath }/myboard/register"><i class="fa fa-edit fa-fw"></i> New Board Register</a>
                        </li>
                        <li>
                            <a href="${contextPath }/login"><i class="fa fa-sign-in fa-fw"></i> Sign in</a>
                        </li>
                        <li>
                            <a href="${contextPath }/logout"><i class="fa fa-sign-out fa-fw"></i> Sign out</a>
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>