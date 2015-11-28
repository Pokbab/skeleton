<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to Ultra Violet</title>
<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<h1>Welcome to Ultra Violet!!</h1>
	<form action="./signUp" method="post">
		<h1>회원가입</h1>
		<table>
			<tr>
				<td>아이디</td>
				<td><input type="text" name="id"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name"></td>
			</tr>
		</table>
		<input type="submit" value="submit">
	</form>
	<footer>
		<address>asdasd</address>
	</footer>
</body>
</html>