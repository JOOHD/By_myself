<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jstlHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
   
<meta charset="UTF-8">
<title>글 내용</title>
<style>
  table {
    width: 100%;
    border: 1px solid #444444;
  }
  th, td {
    border: 1px solid #444444;
  }
</style>
<script type="text/javascript">
function boardDelete(){
	
	
}

function boardUpdate(){
	
	
}
</script>
</head>
<body>
<form>
<table>
        <caption>게시판 글 내용 </caption>
    <tbody>
            <tr>
                <th>제목: </th>
                <td>${result.subject}</td>
            </tr>
            <tr>
                <th>내용: </th>
                <td>${result.context}</td>
            </tr>
            
            <tr>
                <th>조회수: </th>
                <td>${result.views}</td>
            </tr>
            <tr>
                <th>좋아요: </th>
                <td>${result.likes}</td>
         	</tr>
         	
            <tr>
                <td colspan="2">
                    <input type="button" value="수정" onclick="boardDelete()"/>
                    <input type="button" value="삭제" onclick="boardUpdate()"/>
                    <input type="button" value="목록보기" onclick="javascript:location.href='dashboard'"/>
                </td>
            </tr>
    </tbody>
</table>
</form>
</body>
</html>