<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <!-- 
 jQuery 플러그인이 필요하겠죠 저희는 *CDN 방식으로 받겠습니다.	
 *CDN :콘텐츠 전송 네트워크(Content delivery network)는 콘텐츠를 효율적으로 전달하기 위해 여러 노드를 가진 
 네트워크에 데이터를 저장하여 제공하는 시스템을 말한다. 인터넷 서비스 제공자에 직접 연결되어 데이터를 전송하므로, 
 콘텐츠 병목을 피할 수 있는 장점이 있다 
-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<meta charset="UTF-8">
<title>글 작성</title>
<script type="text/javascript">

function boardValidation(){
	
	var subject = $("#subject").val();
	var content = $("#content").val();
	
	if(!subject){
		alert("제목 입력은 필수입니다.");
		$("#subject").focus();
		return false;
	}else if(!content){
		alert("내용 입력은 필수 입니다.");
		$("#content").focus();
		return false;
	}else {
		boardWrite(subject,content);
	}
	
}

function boardWrite(sub,con){
	
	$.ajax({
		
		url : "/jquery/boardwrite",
		type:'POST',
		data : {
			subject : sub,
			context : con
		},
		success:function(data){
			if(data == 1){
				alert("글 등록이 완료되었습니다.");
				location.href="/view/dashboard";
			}else {
				alert("글 등록 실패");
			}
		},error:function(){
			console.log("error");
		}
		
	})
	
}

</script>
</head>
<body>
<form>
<table>
        <caption>게시판 글쓰기 </caption>
    <tbody>
            <tr>
                <th>제목: </th>
                <td><input type="text" placeholder="제목을 입력하세요. " id="subject"/></td>
            </tr>
            <tr>
                <th>내용: </th>
                <td><textarea cols="30" rows="10" placeholder="내용을 입력하세요. " id="content"></textarea></td>
            </tr>
           <!--  <tr>
                <th>첨부파일: </th>
                <td><input type="text" placeholder="파일을 선택하세요. " name="filename"/></td>
            </tr> -->
            <tr>
                <td colspan="2">
                    <input type="button" value="등록" onclick="boardValidation()"/>
                    <input type="button" value="뒤로" onclick="javascript:location.href='dashboard'"/>
                </td>
            </tr>
    </tbody>
</table>
</form>
</body>
</html>