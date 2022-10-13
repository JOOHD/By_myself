<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- fmt를 사용하기위한 태그 라이브러리 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%@ include file="../include/menu.jsp" %>
    <h2>board_list 페이지입니다.</h2>
    <table border="1">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>글쓴이</th>
            <th>작성일자</th>
            <th>조회수</th>
        </tr>
        <!-- forEach 문은 리스트 객체 타입을 꺼낼때 많이 활용된다. -->
        <c:forEach var="row" items="${list}">
            <tr>
                <td>${row.bno}</td>
                <!-- 게시물 조회를 위해서 get방식으로 게시물번호 값을 넘겨주자 
                    - /board/read.do : Controller 매핑을 통해 뷰 리졸버를 거쳐 /WEB-INF/views/board_read.jsp를 반환
                    - ?bno=${row.bno} : GET 메서드를 통해 서버쪽으로 bno란 값에 ${row.bno} 값을 전송한다.
                    - ${row.bno} : 게시글 번호값
                -->
                <td><a href="${path}/board/read.do?bno=${row.bno}">${row.title}</a></td>
                <td>${row.writer}</td>
                <td>
                    <fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />
                </td>
                <td>${row.viewcnt}</td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>
