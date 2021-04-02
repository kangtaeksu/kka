<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="insertForm.do">글쓰기</a>
	<table border="1">
		<tr>
			<td>글번호</td>
			<td>이미지</td>
			<td>글제목</td>
			<td>작성자</td>
			<td>작성일자</td>
			<td>조회수</td>
		</tr>	
		
		<c:forEach var="o" items="${listModel.list}">
			<tr>
				<td>${o.seq}</td>
				<td>
					<c:if test="${o.fname != null }">
						<c:set var="head" value="${fn:substring(o.fname, 
												0, fn:length(o.fname)-4) }"></c:set>
						<c:set var="pattern" value="${fn:substring(o.fname, 
						fn:length(head) +1, fn:length(o.fname)) }"></c:set>
					
						<c:choose>
							<c:when test="${pattern == 'jpg' || pattern == 'gif' }">
								<img src="/MVC/upload/${head }_small.${pattern}">
							</c:when>
							<c:otherwise>
								<c:out value="NO IMAGE"></c:out>
							</c:otherwise>
						</c:choose>
					</c:if>
				</td>
				<td><a href="detailAction.do?seq=${o.seq }">${o.title }</a></td>
				<td>${o.writer }</td>
				<td>${o.regdate }
					<fmt:parseDate var="dt" value="${o.regdate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:parseDate>
					<fmt:formatDate value="${dt}" pattern="yyyy/MM/dd"/>
					<%--${o.regdate}는 문자열이다. formatDate에는 날짜가 들어가야 하기 때문에 parseDate로 날짜 변경 후 그 변수를 넣어줘야한다.--%>
				</td>
				<td>${o.hitcount }</td>
			<tr>
		
		</c:forEach>

	</table>
	<br><br>
	
	<!-- 페이징 영역 -->
	<!-- 태그만 한다고 되는게 아니라 조금 더 복잡한 내용을 가지고 있다.
	이제 서비스에 페이징을 맡긴다. -->

	<!-- 이전 영역 -->
	<c:if test="${listModel.startPage>5 }">
		<a href="listAction.do?pageNum=${listModel.startPage-1}">[이전]</a>
	</c:if>
	
	<!-- 페이지 목록 -->
	<c:forEach var="pageNo" begin="${listModel.startPage}" end="${listModel.endPage}">
		<c:if test="${listModel.requestPage == pageNo}"><b></c:if>
		<a href="listAction.do?pageNum=${pageNo}">[${pageNo}]</a>
		<c:if test="${listModel.requestPage == pageNo}"></b></c:if>
	</c:forEach>
	
	<!-- 이후 영역 -->
	<c:if test="${listModel.endPage < listModel.totalPageCount}">
		<a href="listAction.do?pageNum=${listModel.endPage+1}">[이후]</a>
	</c:if>
	
	<form action="listAction.do" method="post">
		<input type="checkbox" name = "area" value="title"> 제목
		<input type="checkbox" name = "area" value="writer"> 작성자
		<input type="text" name="searchKey" size="10">
		<input type="submit" value="검색">
	</form>
	
	
	
</body>
</html>