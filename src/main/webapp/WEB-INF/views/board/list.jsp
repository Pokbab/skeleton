<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h2>게시판 목록</h2>
	<table>
		<tr>
			<td>글번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>조회수</td>
		</tr>
		<c:forEach items="${boardList}" var="board">
		<tr>
			<td>${board.boardNo}</td>
			<td><a href="/board/detail?boardNo=${board.boardNo}">${board.title}</a></td>
			<td>${board.userNo}</td>
			<td>${board.hit}</td>
		</tr>
		</c:forEach>
	</table>
	<input type="button" id="btnGoWrite" value="글쓰기" onclick="location.href='/board/writeForm'">
</body>
</html>
