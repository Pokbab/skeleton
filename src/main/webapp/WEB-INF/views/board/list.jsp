<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<jsp:include page="/WEB-INF/layout/header.jsp"/>
	<script type="text/javascript" src="/resources/js/list.js"></script>
</head>
<body>
	<h2>게시판 목록</h2>
	<table>
		<tr>
			<td>글번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>조회수</td>
			<td>색변경</td>
			<td>상세보기</td>
		</tr>
		<c:forEach items="${boardList}" var="board" varStatus="status">
		<tr id="row${status.index}" class="row">
			<td>${board.boardNo}</td>
			<td><a href="/board/detail?boardNo=${board.boardNo}">${board.title}</a></td>
			<td>${board.userNo}</td>
			<td>${board.hit}</td>
			<td>
				<input type="button" class="btnRed" value="빨강">
				<input type="button" class="btnYellow" value="노랑">
				<input type="button" class="btnBlack" value="검정">
			</td>
			<td>
				<input type="button" class="btnDetail" value="상세보기" boardNo="${board.boardNo}">
			</td>
		</tr>
		</c:forEach>
	</table>
	<input type="button" id="btnGoWrite" value="글쓰기" onclick="location.href='/board/writeForm'">
	<input type="button" id="btnDetailToggle" value="상세보기 열기/닫기">
	<div id="detailArea">
	</div>
</body>
</html>
