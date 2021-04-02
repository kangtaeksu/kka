
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body><%--이런거 바꾸기 --%>
글번호 :${board.seq }<br>
글제목 :${board.title }<br>
작성자 :${board.writer }<br>
내용 <br>
${board.contents }<br>

작성일 :${board.regdate }<br>
파일명 :<a href="/MVC/download.jsp?filename=${board.fname}">${board.fname}</a>
조회수 :${board.hitcount }<br>
<br><br>

<div>
	<h3>댓글 목록</h3>
	

	<table border="1">
	
		<c:forEach var="o" items="${replyList}">
		<tr>
			<td>댓글번호 : ${o.r_no}</td>
			<td>댓글제목 : ${o.r_title}</td>
			<td>댓글작성자 : ${o.r_writer}</td>
			<td>댓글내용 : ${o.r_contents}</td>
			<td>댓글날짜 : ${o.r_regdate}</td>
		</tr>
		</c:forEach>
	</table>
</div>

<form action="insertReplyAction.do" method="post">
	<input type="hidden" name="seq" value="${board.seq}">
	댓글제목 : <input type="text" name="r_title">
	댓글 작성자 : <input type="text" name="r_writer">
	댓글 내용 : <input type="text" name="r_contents">
	<input type="submit" value="댓글쓰기">
</form>


<br><br>
<a href="listAction.do">글목록</a>
<a href="updateForm.do?seq=${board.seq }">글 수정하기</a>
<a href="deleteAction.do?seq=${board.seq }">글삭제</a>




</body>
</html>