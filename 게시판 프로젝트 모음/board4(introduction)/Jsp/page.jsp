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
    <h2>writer_page 페이지입니다.</h2>
    <form name="bdto" method="post" action="${path}/board/insert.do">
        <div>제목<input name="title" id="title" size="80" placeholder="글 제목 입력"></div>
        <div>내용<textarea name="content" id="content" rows="8" cols="80" placeholder="글 내용 입력"></textarea></div>
        <div>이름<input name="writer" id="writer" placeholder="이름 입력"></div>
        <div style="width:650px; text-align:center;">
            <input type="submit" value="확인">
        </div>
    </form>
</body>
</html>
