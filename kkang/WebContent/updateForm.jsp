
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <%
	BoardDao2 dao = BoardDao2.getInstance();
	Board board = new Board();
	int seq = Integer.parseInt(request.getParameter("seq"));
	board=dao.detailBoard(seq);
%> --%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 글번호를 받아서 DB로부터 글 한개를 받아야함 -->
<!-- 해당 글을 폼에 출력(title, contents) 최소한의 폼 형식이 있어야함 -->


	<form action="updateAction.do" method="post">
	<!-- 히든 많이 씀 (보여줄 필요는 없지만 전달하기 위해서) -->
	<input type="hidden" name="seq" value="${board.seq }">
	작성자 : <input type="text" name="writer" value="${board.writer}"><br>
	제목 : <input type="text" name="title" value="${board.title}"><br>
	내용 <br>
	<textarea rows="6" cols="70" name="contents">${board.contents}</textarea>
	<br>
	<input type="submit" value="수정 완료">
	
	</form>



</body>
</html>