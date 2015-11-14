<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
	<h2>게시판 수정</h2>
	<form action="/board/modify" method="post">
		<table>
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" value="${board.title}"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea rows="20" cols="30" name="contents">${board.contents}</textarea></td>
			</tr>
		</table>
		<input type="submit" id="btnSubmit" value="저장">
		<input type="hidden" name="boardNo" value="${board.boardNo}">
	</form>
</body>
</html>
