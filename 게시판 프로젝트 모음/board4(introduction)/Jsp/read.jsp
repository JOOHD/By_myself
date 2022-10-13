<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%@ include file="../include/menu.jsp" %>
    <h2>board_read 페이지입니다.</h2>
    <a href="${path}/board/writer_page">글쓰기</a>
    <table border="1">
        <th> 정보 </th>
        <th> 데이터 </th>
        <tr>
            <td>작성일자</td>
            <td><fmt:formatDate value="${data.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />
            </td>
        </tr>
        <tr>
            <td>글번호</td><td>${data.bno}</td>
        </tr>
        <tr>
            <td>글제목</td><td>${data.title}</td>
        </tr>
        <tr>
            <td>글내용</td><td>${data.content}</td>
        </tr>
        <tr><td>글쓴이</td><td>${data.writer}</td></tr>
        <tr><td>조회수</td><td>${data.viewcnt}</td></tr>
        
    </table>
    <a href="${path}/board/updatepage?bno=${data.bno}">수정</a>
    <!-- 현재 서버로부터 받은 게시글 번호 값을 GET 방식으로 전달할 수 있게 해놨다. -->
    <a href="${path}/board/delete.do?bno=${data.bno}">삭제</a>
    
</body>
</html>
