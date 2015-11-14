<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
	<h2>게시판 글쓰기</h2>
	<form action="/board/write" method="post">
		<table>
			<tr>
				<td>제목</td>
				<td><input type="text" name="title"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea rows="20" cols="30" name="contents"></textarea></td>
			</tr>
		</table>
		<input type="submit" id="btnSubmit" value="저장">
	</form>
</body>
</html>
