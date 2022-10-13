-- DB 생성
CREATE DATABASE user CHARACTER SET [character set];

-- DB 선택
USE user

-- DB 삭제
DROP DATABASE user

-- 테이블 생성
CREATE TABLE student
(
    ID INT,
    Name VARCHAR(30),
    BirthDay DATE,
    Age INT
);

-- 테이블 삭제
DROP TABLE student;

-- 필드 추가
ALTER TABLE student ADD Class VARCHAR(30);
-- 필드 수정
ALTER TABLE student MODIFY ID VARCHAR(30);
-- 필드 삭제
ALTER TABLE student DROP Age;

-- 레코드 추가
INSERT INTO ID, Class, Age VALUES (1, '3반', '14');
-- 레코드 내용 수정
INSERT INTO student SET Age = 19 WHERE ID = 1;
-- 레코드 삭제
DELETE FROM student WHERE ID = 1;

CREATE DATABASE user CHARACTER SET 
USE user
DROP DATABASE user

CREATE TABLE student 
(
    ID INT, 
    Name VARCHAR(30),
    Birthday DATE,
    Age int
);

DROP TABLE student;

ALTER TABLE student ADD Class VARCHAR(30);
ALTER TABLE student MODIFY ID VARCHAR(30);
ALTER TABLE student DROP Age;

INSERT INTO ID, Class, Age VALUES (1, '3반', '14');
INSERT INTO student SET Age = 20 WHERE ID = 2;
DELETE FROM student WHERE ID = 2;

Join 
SELECT 
테이블이름.조회할 테이블,
테이블이름.조회할 테이블,
FROM 기준테이블 이름
(INNER, LEFT, RIGHT FULL) JOIN 조인테이블 이름
ON 기준테이블이름.기존키 = 조인테이블이름.기준키;

SELECT (테이블이름.조회할 테이블)users.id, 
                               users.name, 
                               users.age, 
                               users.gender, 
                               account.account_type
FROM (기준테이블이름.기존키)users 
JOIN (조인테이블 이름)account 
ON   (기존테이블이름.기존키)account.id = (조인테이블이름.기준키)users.account_id;

SELECT users.id, users.name, users.age, users.gender, account.account_type
FROM users JOIN account ON account.id = users.account_id;

CREATE DATABASE database_name CHARACTER SET character set;
-- 데이버베이스 선택
USE database_name;
DROP DATABASE database_name;
-- 테이블 생성
CREATE TABLE User
(
    ID INT,
    Name VARCHAR(30),
    Email VARCHAR(30),
    Age INT
);

-- 테이블 필드 추가
ALTER TABLE User ADD PhoneNumber INT;
-- 테이블 필드 타입 변경
ALTER TABLE User MODIFY ID VARCHAR(20);
-- 테이블 필드 삭제
ALTER TABLE User DROP Age;

-- 테이블 레코드 추가
INSERT INTO [ID, Name, BirthDay] VALUES (1, '주현돈', '1995-07-28');
-- 테이블 레코드 내용 수정
UPDATE User SET Age = 28 WHERE Name = '주현돈';
-- 테이블 레코드 삭제
DELETE FROM User WHERE Age = 28;

-- 테이블 필드 추가
ALTER TABLE User ADD PhoneNumber INT;
-- 테이블 필드 타입 변경
ALTER TABLE User MODIFY ID VARCHAR(20);
-- 테이블 필드 삭제
ALTER TABLE User DROP Age;

-- 테이블 레코드 추가 
INSERT INTO [ID, Name, Addr] VALUES (1, '주현돈', '성북구 정릉 3동');
-- 테이블 레코드 내용 수정
UPDATE User SET ID = 2 WHERE Name '주현돈';

Join
SELECT 
테이블이름.조회할 테이블,
테이블이름.조회할 테이블,
FROM 기준테이블 이름
(INNER, LEFT, RIGHT FULL) JOIN 조인테이블 이름
ON 기준테이블이름.기준키 = 조인테이블이름.기준키;

CREATE TABLE Reservation(ID INT, Name VARCHAR(30), ReserveDate DATE, RoomNum INT);
CREATE TABLE Customer(ID INT, Name VARCHAR(30), Age INT, Address VARCHAR(20));

INSERT INTO Reservatino(ID, Name, ReserveDate, RoomNum) 
                 VALUES(1, '홍길동', '2016-01-05', 2014);

INSERT INTO Customer(ID, Name, Age, Address) 
                 VALUE(1, '김시민', 17, '진주');

-- 테이블 칼럼 추가
ALTER TABLE PLAYER ADD (ADDRESS VARCHAR(30));
-- 테이블 칼럼 삭제
ALTER TABLE PLAYER DROP COLUMN ADDRESS;
-- 컬럼 수정
ALTER TABLE TEAM_TEMP MODIFY (ORIG_YYYY VARCHAR(8) DEFAULT '20200129' NOT NULL);
-- 칼럼명 변경
ALTER TABLE PLAYER RENAME COLUMN PLAYER_ID TO TEAM_IDL;
-- 태이블 내 컬럼 추가
INSERT INTO Reservation(ID, Name, ReserveDate, RoomNum) VALUES (1, '주현돈', '2022-08-04', 2022);

ALTER TABLE PLAYER ADD (ADDRESS VARCHAR(30));
ALTER TABLE PLAYER DROP COLUMN ADDRESS;
ALTER TABLE TEAM_TEMP MODIFY (ORIG_YYYY VARCHAR(8) DEFAULT '20200129' NOT NULL);
ALTER TABLE PLAYER RENAME COLUMN PLAYER_ID TO TEAM_IDL;
INSERT INTO Reservation(ID, Name, ReserveDate, RoomNum) VALUES (1, '주현돈', '2022-08-04', 2022);

ALTER TABLE PLAYER ADD (ADDRESS VARCHAR(30));
ALTER TABLE PLAYER DROP COLUMN ADDRESS;
ALTER TABLE TEAM_TEMP MODIFY (ORIG_YYYY VARCHAR(8) DEFAULT '20220804' NOT NULL);
ALTER TABLE PLAYER RENAME COLUMN PLAYER_ID TO TEAM_IDL;
INSERT INTO Reservation(ID, Name, ReserveDate, RoomNum) VALUES (1, '주현돈', '2022-08-04', 2022);

UPDATE EMPLOYEE
    SET WORKDEPT = 'D11', 
        PHONENO = '7213',
        JOB = 'DESIGNER',
    WHERE EMPNO = '000270';

-- 테이블 행 제거
DELETE FROM CORPDATA.EMPLOYEE
WHERE WORKDEPT = 'D11';

-- 테이블 데이터 조회
SELECT Nm_Kor,Age FROM My_Table WHERE Age=25

-- 인덱스 생성
CREATE INDEX grade_index_score
ON student.grade (score ASC);

-- 인덱스 삭제
ALTER TABLE Reservation
DROP INDEX NameIdx;  

-- 작가 등록
INSERT INTO vam_board(title, content, writer) VALUES ('테스트 제목', '테스트 내용', '작가');

-- 게시판 목록
SELECT * FROM vam_board;

-- 게시판 조회
SELECT * FROM vam_board WHERE bno = 8;
SELECT * FROM vam_board WHERE bno = 8;

-- 게시판 수정
UPDATE vam_board SET title='제목 수정', conten='내용 수정', updateDate=SYSDATE WHERE bno = 8;

-- 게시판 삭제
DELETE FROM vam_board WHERE bno = '삭제할 게시판 번호';
DELETE FROM vam_board WHERE bno = '삭제할 게시판 번호';

-- 재귀 복사
INSERT INTO vam_board (title, content, writer)(SELECT title, content, writer FROM vam_board);

-- 행 갯수 확인
SELECT COUNT(*) FROM vam_board;
-- 테이블에서 행 name이 'A'인 모든 행 구하기
SELECT COUNT(*) FROM sample51 WHERE name = 'A';
/* 
   행 no, name 모두 구하지만, name은 null은 제외, 
   다만 COUNT(*)와 같은 경우 모든 행의 갯수를 구하는 것이기 때문에 NULL값에 영향을 받지 않는다.
*/
SELECT COUNT(no), COUNT(name) FROM sample51;

SELECT ALL name FROM sample51;
-- 중복된 값을 제거하는 DISTINCT 함수를 제공한다.
SELECT DISTINCT name FROM sample51;
-- name열에서 NULL 값을 제외하고, 중복하지 않는 데이터의 개수를 구하는 경우를 생각해보자.
SELECT COUNT(ALL name), COUNT(DISTINCT name) FROM sample51;


--------------------------------------------------------------------- rowNum

-- 조회 개수를 제한하는 경우
-- 오라클 예시 : WHERE ROWNUM <= 5
SELECT TOP(5)
       ename,
       job,
       sal,
  FROM emp   

-- WITH TIES 키워드를 사용하면 마지막 순위와 같은 값을 가진 행인 경우 모두 표시한다.
SELECT TOP(4) WITH TIES
       job
     , deptno
  FROM emp
 ORDER BY job, deptno  


-- 조회 순번을 매기는 경우
-- SELECT 1은 의미없는 코드
SELECT ROW_NUMBER() OVER(ORDER BY (SELECT 1)) AS rownum
     , ename
     , job
     , sal
  FROM emp
 WHERE sal > 2000

-- ORDER BY에 정렬 컬럼을 사용하면 정렬 후 순번이 부여된다.
SELECT ROW_NUMBER() OVER(ORDER BY sal DESC) AS rownum
     , ename
     , job
     , sal
  FROM emp
 WHERE sal > 2000 


-- PARTITION BY를 사용하면 그룹별 순번을 부여할 수 있다.
SELECT ROW_NUMBER() OVER(ORDER BY (SELECT 1)) AS rownum
     , ename
     , job
     , sal
  FROM emp
 WHERE sal > 2000

--------------------------------------------------------------------- foreach
<foreach collection="LIST or Array" item="alias"></foreach>

<select id="selectPstIn" resultType="domain.blog.Post">
    SELECT *        -- db 불러오기
    FROM POST P     -- POST P 테이블
    WHERE ID in     -- ID in
    <foreach item="item" index="index" collection="list"
     open="(" seperator="," close=")">
      #{item}
    </foreach>
</select>      

---------------------------------------------------------------------- 배열 예시
String[] userArray = {"1", "2", "3"}

-- 1-1 배열 파라미터를 Map을 통해 넘겼을 경우
-- DAO
public List<Members> getAuthUserList(String[] userArray) {

    HashMap<Stirng, Object> map = new HashMap<String, Object>();

    map.put("userArray", userArray);

    return sqlSession.selectList("getAuthUserList", map);
}

-- user-Mapper.xml
<select id="getAuthUserList" resultType="members">
SELECT m.*, a.name FROM members AS m
JOIN auhtority AS a ON m.auhtority = a.auhtority
WHERE m.auhtority 
IN -- IN 연산자는 조건의 범위를 지정하는 데 사용된다. 값은 콤마(,)로 구분하여 괄호 내에 묶으며, 이 값 중에서 하나 이상과 일치하면 조건에 맞는 것
<foreach collection="userArray" item="arr" open="(" close=")" seperator=",">
    #{arr}
</foreach>
ORDER BY m.auhtority;
</select>    
-- ※ 주의 : collection을 꼭! 넘겨주는 배열 변수 값과 동일하게 작성하셔야 합니다

-- 1-2 배열 파라미터를 직접 넘겼을 경우
-- DAO
public List<Members> getAuthUserList(String[] userArray) {

    return sqlSession.selectList("getAuthUserList", userArray);
}

-- user-Mapper.xml
<select id="getAuthUserList"  resultType="members">
SELECT m.*,a.name FROM members AS m 
JOIN authority AS a ON m.authority = a.authority
WHERE m.authority IN
<foreach collection="array" item="arr" open="(" close=")" separator=",">
 #{arr}
</foreach>
ORDER BY m.authority;
</select>


---------------------------------------------------------------------- 리스트 예시
List<Members> chKList = userService.getUserList(); -- SELECT * FROM members 결과값

-- 2-1. 리스트 Map을 통해 넘겼을 경우
-- DAO
public List<Members> getListTest(List<Members> chkList) {
	
    	HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("chkList",chkList);

		return sqlSession.selectList("getListTest", map);
}

-- user-Mapper.xml
<select id="getListTest" resultType="members">
SELECT m.*,a.name FROM members AS m 
JOIN authority AS a ON m.authority = a.authority
WHERE m.authority IN
<foreach collection="chkList" item="item" open="(" close=")" separator=",">
 #{item.authority}
</foreach>
ORDER BY m.authority;
</select>
-- 리스트 안에 뽑고 싶은 결괏값을 {key.value} 형태로 뽑으시면 됩니다.

-- 2-2. 리스트 파라미터를 직접 넘겼을 경우
-- DAO
public List<Members> getListTest(List<Members> chkList) {

		return sqlSession.selectList("getListTest", chkList);
}

--user-Mapper.xml
<select id="getListTest" resultType="members">
SELECT m.*,a.name FROM members AS m 
JOIN authority AS a ON m.authority = a.authority
WHERE m.authority IN
<foreach collection="list" item="item" open="(" close=")" separator=",">
 #{item.authority}
</foreach>
ORDER BY m.authority;
</select>

--공통 설명
1. 먼저 리스트/배열 변수 값을 collection에 넣어주고, item이라는 설정으로 별칭 설정을 해준다.
2. 리스트/배열의 값이 시작하기 전 open="(" 이 설정돼있으므로'(' (열린 괄호)가 열리게 되고
3. 리스트/배열의 값이 한 번씩 반복문을 거칠 때마다 separator 옵션에 있는 ', '(콤마)가 찍히게 된다.
4. 반복이 끝나면 close=")" 설정이 있으므로 ')' (닫힌 괄호)가 쓰인다.

------------------ 맴버 테이블에서 데이터 넘겨받아 리스트로 반복문 돌리기
-- JSP
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<meta charset="UTF-8">
<script type="text/javascript">
var chkArray = new Array;
$(document).ready(function(){

	getAuthUserList($("input[name=chk_authority]:checked").val());
	
$("input[name=chk_authority]").change(function(){

	var chk = "";
	chkArray = [];
	
	$("input[name=chk_authority]:checked").each(function() {
	
		chkArray.push($(this).val());

	});
	
		
	if(chkArray.length == 0){
		$("#userList").empty();
	} else {
		getAuthUserList(chkArray);
	}
	
	});

});

function getAuthUserList(chkArray){
	
$.ajax({
		
		url : "/user/getAuthUserList",
		data : { chkArray : chkArray },
		traditional : true,
		async: true,
		success : function(data){
			var html = '';
			
			for(key in data){
			html += '<tr>';
			html += '<td>'+data[key].userId+'</td>';
			html += '<td>'+data[key].nickname+'</td>';
			html += '<td>'+data[key].name+'</td>';
			html += '</tr>';
			}
			
			
			$("#userList").empty().append(html);
			
		}
	})
	
}

</script>
</head>
<body>

<div>
유저 등급별 보기<br>
<input type="checkbox" name="chk_authority" value="0" checked="checked">실버
<input type="checkbox" name="chk_authority" value="1">골드
<input type="checkbox" name="chk_authority" value="2">플래티넘
<input type="checkbox" name="chk_authority" value="3">다이아
<input type="checkbox" name="chk_authority" value="4">마스터
<input type="checkbox" name="chk_authority" value="5">그랜드마스터

<table>
<thead>
<tr>
<th>아이디</th>
<th>닉네임</th>
<th>권한</th>
</tr>
</thead>
<tbody id="userList">
</tbody>
</table>
</div>
</body>
</html>

-- [배열(array)] 예시 1. 멤버 테이블에서 체크박스에 따라 권한별 보여주는 기능 만들기
-- Controller
@ResponseBody
@RequestMapping("user/getAuthUserList")
public List<Members> getAuthUserList(String[] chkArray) {
		
        List<Members> result = userService.getAuthUserList(chkArray);
		
        return result;
}

-- Service
List<Members> getAuthUserList(String[] chkArray);

-- Servicelmpl
@Override
public List<Members> getAuthUserList(String[] chkArray) {

		return userDAO.getAuthUserList(chkArray);
}

-- DAO
public List<Members> getAuthUserList(String[] chkArray) {
		
        HashMap<String, Object> map = new HashMap<String, Object>();
		
        map.put("chkArray",chkArray);
		
        return sqlSession.selectList("getAuthUserList", map);
}

--user-Mapper.xml
<select id="getAuthUserList"  resultType="members">
    SELECT m.*, a.name FROM members AS m 
    JOIN authority AS a ON m.authority = a.authority
    WHERE m.authority IN
        <foreach collection="chkArray" item="item" open="(" close=")" separator=",">
        #{item}
        </foreach>
    ORDER BY m.authority;
</select>

--쿼리 결과
SELECT m.*,a.name FROM members AS m  
JOIN authority AS a ON m.authority = a.authority 
WHERE m.authority IN

-- (체크박스 수에 따른 값)
    #('1','2','3')
    #('2','3')
    ORDER BY m.authority;


-- 쿼리 결과 설명

-- 1. 먼저 배열 변수 값을 collection에 넣어주고, item이라는 설정으로 별칭 설정을 해준다.
-- 2. 배열의 값이 시작하기 전 open="(" 이 설정돼있으므로'(' (열린 괄호)가 열리게 되고
-- 3. 배열의 값이 한 번씩 반복문을 거칠 때마다 separator 옵션에 있는 ', '(콤마)가 찍히게 된다.
-- 4. 반복이 끝나면 close=")" 설정이 있으므로 ')' (닫힌 괄호)가 쓰인다.


-- [리스트(List)] 예시 2. 멤버 테이블에서 데이터 넘겨받아 리스트로 반복문 돌리기
-- Controller
@ResponseBody 
@RequestMapping("user/getUserList")
	public List<Members> getUserList() {

		List<Members> result = userService.getUserOne();

		List<Members> foreachTest = userService.getListTest(result);

		System.out.println(foreachTest);

		return result;
}

-- Service
List<Members> getListTest(List<Members> result);

-- ServiceImpl
@Override
public List<Members> getListTest(List<Members> result) {

		return userDAO.getListTest(result);
}

-- DAO
public List<Members> getListTest(List<Members> chkList) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("chkList",chkList);

		return sqlSession.selectList("getListTest", map);
}

-- user-Mapper.xml
<select id="getListTest" resultType="members">
    SELECT m.*,a.name FROM members AS m 
    JOIN authority AS a ON m.authority = a.authority
    WHERE m.authority IN
        <foreach collection="chkList" item="item" open="(" close=")" separator=",">
        #{item.authority}
        </foreach>
    ORDER BY m.authority;
</select>

-- 쿼리 결과

SELECT m.*,a.name FROM members AS m  
JOIN authority AS a ON m.authority = a.authority 
WHERE m.authority IN

-- (체크박스 수에 따른 값)
-- #('1','2','3')
-- #('2','3')
ORDER BY m.authority;

-- 쿼리 결과 설명
-- 1. 먼저 리스트 변수 값을 collection에 넣어주고, item이라는 설정으로 별칭 설정을 해준다.
-- 2. 리스트의 값이 시작하기 전 open="(" 이 설정돼있으므로'(' (열린 괄호)가 열리게 되고
-- 3. 리스트의 값이 한 번씩 반복문을 거칠 때마다 separator 옵션에 있는 ', '(콤마)가 찍히게 된다.
-- 4. 반복이 끝나면 close=")" 설정이 있으므로 ')' (닫힌 괄호)가 쓰인다.