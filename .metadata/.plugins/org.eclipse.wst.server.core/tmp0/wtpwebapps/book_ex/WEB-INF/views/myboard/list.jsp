<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보드 리스트</title>
</head>
<body>

<table id="boards">
	
		<caption style="font-size: 15pt;">회원리스트</caption>
		<tr>
			<th>bno</th>
			<th>btitle</th>
			<th>bcontent</th>
			<th>bwriter</th>
			<th>bregdate</th>
			<th>bmodate</th>
			<th>bviewcnt</th>
			<th>breplycnt</th>
			<th>bdeflag</th>
		</tr>
		<c:forEach var="board" items="${boardList}">
			<tr>
				<td><c:out value="${board.bno}"></c:out></td>
				<td><c:out value="${board.btitle}"></c:out></td>
				<td><c:out value="${board.bcontent}"></c:out></td>
				<td><c:out value="${board.bwriter}"></c:out></td>
				<td><c:out value="${board.bregdate}"></c:out></td>
				<td><c:out value="${board.bmodate}"></c:out></td>
				<td><c:out value="${board.bviewcnt}"></c:out></td>
				<td><c:out value="${board.breplycnt}"></c:out></td>
				<td><c:out value="${board.bdeflag}"></c:out></td>
			</tr>
		</c:forEach>

</table>


</body>
</html>