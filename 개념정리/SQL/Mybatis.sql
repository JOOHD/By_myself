Mybatis 
SQL Map XML (first class)
cache : 해당 네임스페이스를 위한 캐시 설정
cache-ref : 다른 네임스페이스의 캐시 설정에 대한 참조
resultMap : 데이터베이스 결과 데이터를 객체에 로드하는 방법을 정의하는 엘리먼트 


id : 구문을 찾기 위해 사용될 수 있는 네임스페이스내 유일한 구분자
parameterType : 구문에 전달될 파라미터의 패키지 경로를 포함한 전체 클래스명이나 별칭
resultType : 이 구문에 의해 리턴되는 기대타입의 패키지 경로를 포함한 전체 클래스명이나 별칭. 
             collection인 경우 collection 타입 자체가 아닌 collection 이 포함된 타입이 될 수 있다. resultType이나 resultMap을 사용하라.
resultMap : 외부 resultMap 의 참조명. 결과맵은 마이바티스의 가장 강력한 기능이다. resultType이나 resultMap을 사용하라.
keyProperty : (입력 (INSERT, UPDATE)에만 적용) getGeneratedKeys 메소드나 INSERT 구문의 selectKey 하위 엘리먼트에 의해
                리턴된 키를 셋팅할 프로퍼티를 지정. 디폴트는 셋팅하지 않는다. 여러개의 칼럼을 사용한다면 프로퍼티명에 콤마를 구분자로 나열 가능

-- SELECT
-- 데이터베이스에서 데이터를 가져온다. 마이바티스는 데이터를 조회하고 그 결과를 매핑하는데 집중하고 있다.

<SELECT id="selectPerson" parameterType="int" resultType="hashmap">
    SELECT * FROM PERSON WHERE ID = #{id}
</SELECT>

-- 이 구문의 이름은 selectPerson이고 int 타입의 파라미터를 가진다. 그리고 결과 데이터는 HashMap 에 저장된다.
/*
 {id} 이 표기법은 마이바티스에게 PreparedStatement 파라미터를 만들도록 지시한다. JDBC를 사용할 때
    PreparedStatement에는 "?" 형태로 파라미터가 전달된다. 즉 결과적으로 위 설정은 아래와 같이 작동하게 되는 셈이다
*/

-- JDBC 코드와 유사함, 마이바티스 코드는 아님
String selectPerson = "SELECT * FROM PERSON WHERE ID=?";
PreparedStatement ps = conn.preparedStatement(selectPerson);
ps.setInt(1,id);

-- INSERT
<INSERT id="insertAuthor">
    INSERT INTO Author (id, username, password, email, bio)
    VALUES (#{id}, #{usernmae}, #{password}, #{bio})
</INSERT>

-- INSERT 는 KEY 생성과 같은 기능을 위해 몇가지 추가 속성과 하위 엘리먼트를 가진다.
    <INSERT id = "insertAuthor" useGeneratedKeys = "true"
        keyProperty = "id">
      INSERT INTO Author (username, password, email, bio)
      VALUES (#{username}, #{password}, #{email}, #{bio})
    </INSERT>

-- 마이바티스는 자동생성키 칼럼을 지원하지 않는 다른 데이터베이스를 위해 다른 방법 또한 제공한다.
<INSERT id="insertAuthor">
    <selectKey keyProperty="id" resultType="int" order="BEFORE">
        SELECT CAST(RANDOM() * 10000000 AS INTEGER) A FROM SYSIBM, SYSDUMMY1
    </selectKey>
    INSERT INTO Author
        (id, username, password, email, bio, favorite_section)
    VALUES
        (#{id}, #{usernmae}, #{password}, #{bio}, #{favoriteSection, jdbcType=VARCHAR})
</INSERT>

-- sql propertiy 구문
-- 프로퍼티 값은 다음처럼 refid 속성이나 include 절 내부에서 프로퍼티값으로 사용할 수 잇따.
<sql id="sometable">
    ${prefix} Table
</sql>

-- sql include 구문
<sql id="someinclude">
    from
      <include refid="${include_target}"/>
</sql>

/*
properties : 1.응용프로그램의 구성 가능한 파라미터를 저장하기위해 자바 관련 기술을 사용하는 파일들을 위한 파일 확장
             2.properties의 각 줄은 일반적으로 하나의 프로퍼티를 저장 (키=값, 키=값, 키:값, 와 같이 여러 형태)
             3.각 파라미터는 문자열들의 일부로 저장되며, 문자열은 파라미터의 이름(키)을 저장하며, 다른 하나는 값을 저장
             4.refid속성이나 include절 내부에서 프로퍼티값으로 사용할 수 있다.
*/
<select id="select" resultType="map">
  select
    field1, field2, field3
  <include refid="someinclude">
    <property name="prefix" value="Some"/>
    <property name="include_target" value="sometable"/>
  </include>
</select>

-- Parameters 
<select id="selectUsers" resultType="User">
  select id, username, password
  from users
  where id = #{id}
</select>

-- integer, String 같은 간단한 원시타입은 프로퍼티를 가지지 않는다. 복잡한 객체를 전달하고자 한다면 다음의 예제처럼 상황은 조금 다르게 된다.
<insert id="insertUser" parameterType="User">
  insert into users (id, username, password)
  values (#{id}, #{username}, #{password})
</insert>

-- User타입의 파라미터 객체가 구문으로 전달되면 id, username, password 프로퍼티는 찾아서 PreparedStatement로 전달된다.
-- javaType은 파라미터 객체의 타입을 판단한느 기준이 된다. javaType은 TypeHandler를 사용하여 정의 가능.
/*
  위 예제에서 selectKey 구문이 먼저 실행되고 Author id 프로퍼티에 셋팅된다. 그리고 나서 insert 구문이 실행된다.
  이건 복잡한 자바 코드 없이도 데이터베이스에 자동생성키의 행위와 비슷한 효과를 가지도록 해준다. selectKey 엘리먼트는 다음처러 설정가능
*/
<selectKey                      -- selectKey 구문의 결과가 셋팅될 대상 프로퍼티
    keyProperty="id"            -- 리턴되는 결과셋의 칼럼명은 프로퍼티에 일치한다. 여러개의 칼럼을 사용한다면 칼럼명의 목록은 콤마를 사용해서 구문한다.
    resultType="int"            -- 결과의 타입. 마이바티스는 이 기능을 제거할 수 있지만 추가하는데 문제가 되지 않음. 마이바티스는 String을 포함하여 키로 사용될 수 있는 간단한 타입 허용
    order="BEFORE"              /* BEFORE 또는 AFTER를 셋팅할 수 있다. BEFORE로 설정하면 키를 먼저 조회하고 그 값을 keyProperty 에 셋팅한 뒤 insert 구문을 실행한다. 
                                   AFTER로 설정하면 insert 구문을 실행한 뒤 selectKey 구문을 실행한다. 오라클과 같은 데이터베이스에서는 insert구문 내부에서 일관된 호출형태로 처리한다.
                                */
    statementType="PREPARED">   -- 위 내용과 같다. 마이바티스는 Statement, PreparedStatement 그리고 CallableStatement을 매핑하기 위해 STATEMENT, PREPARED 그리고 CALLABLE 구문타입을 지원한다.


-- UPDATE
<UPDATE id="updateAuthor">
    UPDATE Author SET
        username = #{username},
        password = #{password}.
        bio = #{bio}
    WHERE id = #{id}
</UPDATE>

-- DELETE
<DELETE id="deleteAuthor">
    DELETE FROM AUTHOR WHERE id = #{id}
</DELETE>

-- SQL
-- 다른 구문에서 재사용가능한 SQL 구문을 정의할 때 사용된다. 로딩시점에 정적으로 파라미터처럼 사용가능.
<SQL id="userColumns"> 
        ${alias}.id,
        ${alias}.username,
        ${alias}.password,
</SQL>

-- SQL 조각은 다른 구문에 포함시킬수 있다.
<SELECT id="selectUsers" resultType="map">
  SELECT
    <include refid="userColumns"><property name="alias" value="t1"/></include>,
    <include refid="userColumns"><property name="alias" value="t2"/></include>
  FROM some_table t1
    CROSS JOIN some_table t2
</SELECT>

-- 프로퍼티값은 다음처럼 refid 속성이나 include 절 내부에서 프로퍼티값으로 사용할 수 있다.ALTER
<SELECT id="selectUsers" resultType="map">
    SELECT
        <INCLUDE refid="userColumns"><property name="alias" VALUES="t1"/></INCLUDE>
        <INCLUDE refid="userColumns"><property name="alias" VALUES="t2"/></INCLUDE>
    FROM some_table t1
     CROSS JOIN some_table t2
</SELECT>

/*

    PreparedStatement

    String sqlstr = "SELECT name, memo FROM TABLE WHERE num = ?"
    PreparedStatement stmt = conn.prepareStatement(sqlstr);
    pstmt.setInt(1, num);
    ResultSet rst = pstmt.executeQuerey();
    => 메모리에 올라가게 되므로 동일한 쿼리의 경우 , 매번 컴파일 되지 않아도 된다는 이점 성능 ↑

    Statement

    String sqlstr = "SELECT name, memo FROM TABLE WHERE num = " + num 
    Statement stmt = conn.credateStatement(); 
    ResultSet rst = stmt.executeQuerey(sqlstr); 
    executeQuerey() 할 때 마다 매번 컴파일함 성능 ↓

*/

/*
    #{} 와 ${} 차이점
    
    1.#{} 사용시 PreparedStatement 생성
    2.PreparedStatement 매개 변수 값 안전하게 설정
    3.PreparedStatement 가 제공하는 set 계열의 메소드를 사용하여 ?를 대체할 값을 지정.
    4.들어오는 데이터 문자열로 인식하기 때문에 자동 따옴표 붙음

    사용 이유
    안전하고 빠르기 때문에 선호
*/

SELECT count(*) FROM 
ExUSER_TB
WHERE USER_ID = #{id} AND USER_PW = #{pw}

SELECT count(*) FROM ExUSER_TB 
WHERE
USER_ID = ? AND USER_PW = ?

/*
    #{} 와 ${} 차이점
    
    1.${} 사용시 Statement 생성
    2.Statement 매개변수 값 그대로 전달
    3.전달하기 때문에 문자열에 따옴표가 붙지 않는다.
      ex)
          SELECT * FROM Extable WHERE Statementparameter = 홍길동
    4.테이블 컬럼 타입이 varchar 여도 숫자 그대로 들어가기 떄문에
      ex)
          SELECT * FROM Extable WHERE Statementparameter(varchar 타입) = 1

    사용 이유
    ORDER BY 함수를 사용할 때 오히려 자동 따옴표가 붙으면 함수가 안먹기 때문에 ${}를 사용해야한다.
*/

SELECT count(*) FROM
ExUSER_TB
WHERE USER_ID = "${id}" AND USER_PW = "${pw}"
ORDER BY ${columnName}

/*
    정리
    1.#{} 자동으로 값에 따옴표가 붙고 성능 좋음
    2.${} 값 그대로 전달 따옴표 안붙음 성능 좋지 않음 보안 취약
    3.ORDER BY 아니면 #{} 쓰기.
*/


<if>  : 단일 조건문
<choose> <when> <otherwise> : 다중 조건문

--1.paraName1 이라는 파라미터가 null이 아니면서 값이 "test"와 동일한가?
<if test='paraName1 != null  and(paraName1 eq "test".toString())'>
</if>

--2.paraName1 이라는 파라미터가 "all" 이라는 문자와 동일 하지 않은가?
<if test='!paraName1.equals("all")'>
</if>


--paraName1 이라는 파라미터의 값이 3보다 큰가?
<if test='paraName1 > 3'></if>    

--paraName1 이라는 파라미터의 값이 3보다 크거나 같은가?                                     
<if test='paraName1 >= 3'></if>

--paraName1 이라는 파라미터의 값이 3보다 작은가?  
<if test='paraName1 < 3'></if>

--paraName1 이라는 파라미터의 값이 3보다 작거나 같은가?
<if test='paraName1 <= 3'></if>

--paraName1 이라는 파라미터의 값이 숫자로 된 문자열일 경우
<if test='paraName1 > "3"'></if>
--비교할 값을 쌍 따옴표로 묶어준다.

--주의
요소 유형 "null"과(와) 연관된 "test" 속성의 값에는 '<' 문자가 포함되지 않아야 합니다.

--원인
if 태그 안에 ">" 괄호를 인식하지 못하는 거다. 그렇다면 CDATA를 쓰면 어떨까? 역시 적용되지 않는다.
일단 원인은 ">" 괄호를 XML Parsing으로 인식한 건데, XML Parsing을 Text로 바꿔주는 CDATA 마저 적용되지 않는 현상

--해결
기호           대체식   예제
<               lt      <if test="paraName1 lt 0">
>               gt      <if test="paraName1 gt 0"> 
<= (또는 =<)    lte     <if test="paraName1 lte 0">
>= (또는 =>)    gte     <if test="paraName1 gte 0">

-- paraName1 값이 Y이거나 paraName2의 값이 N인 경우

OR
-- <if> 문
    <if test = 'paaraName1 == "Y" or paraName2 == "N"'>

-- <choose> 문
    <choose>
    <when test = 'paraName1 == "Y" or paraName2 == "N"'> 
    </choose

AND 
-- <if> 문
    <if test='paraName1 == "Y" and paraName2 == "N"'></if>

-- <choose> 문
    <choose>
    <when test='paraName1 == "Y" and paraName2 == "N"'>
    </choose>

-- MyBatis foreach문 지원 태그
collection -- 전달받은 인자. List or Array 형태만 가능
item -- 전달받은 인자 값을 alias 명으로 대체
open -- 구문이 시작될때 삽입할 문자열
close -- 구문이 종료될때 삽입할 문자열
separator -- 반복 되는 사이에 출력할 문자열
index -- 반복되는 구문 번호이다. 0부터 순차적으로 증가

<foreach collection="List or Array" item="alias" ></foreach>

-- 사용 예시
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list"
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>

-- 1. 배열 예시
String[] userArray = {"1","2","3"}

-- 1-1. 배열 파라미터를 Map을 통해 넘겼을 경우
-- DAO
public List<Members> getAuthUserList(String[] userArray) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userArray",userArray);

		return sqlSession.selectList("getAuthUserList", map);
}

-- user-Mapper.xml
<select id="getAuthUserList"  resultType="members">
    SELECT m.*,a.name FROM members AS m 
    JOIN authority AS a ON m.authority = a.authority
    WHERE m.authority IN
        <foreach collection="userArray" item="arr" open="(" close=")" separator=",">
        #{arr}
        </foreach>
    ORDER BY m.authority;
</select>
-- ※ 주의 : collection을 꼭! 넘겨주는 배열 변수 값과 동일하게 작성하셔야 합니다.

-- 1-2. 배열 파라미터를 직접 넘겼을 경우
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
-- ※ 주의 : collection을 꼭! "array"로 작성하셔야 합니다.


-- 2. 리스트 예시
List<Members> chkList = userService.getUserList(); -- SELECT * FROM members 결과값


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
-- ※ 주의 : collection을 꼭! 넘겨주는 리스트 변수 값과 동일하게 작성하셔야 합니다.


-- 2-2. 리스트 파라미터를 직접 넘겼을 경우
-- DAO
public List<Members> getListTest(List<Members> chkList) {
		return sqlSession.selectList("getListTest", chkList);
}

-- user-Mapper.xml
<select id="getListTest" resultType="members">
    SELECT m.*,a.name FROM members AS m 
    JOIN authority AS a ON m.authority = a.authority
    WHERE m.authority IN
        <foreach collection="list" item="item" open="(" close=")" separator=",">
        #{item.authority}
        </foreach>
    ORDER BY m.authority;
</select>
-- 리스트 안에 뽑고 싶은 결괏값을 {key.value} 형태로 뽑으시면 됩니다.
-- ※ 주의 : collection을 꼭! "list"로 작성하셔야 합니다.


-- 공통 설명

-- 1. 먼저 리스트/배열 변수 값을 collection에 넣어주고, item이라는 설정으로 별칭 설정을 해준다.
-- 2. 리스트/배열의 값이 시작하기 전 open="(" 이 설정돼있으므로'(' (열린 괄호)가 열리게 되고
-- 3. 리스트/배열의 값이 한 번씩 반복문을 거칠 때마다 separator 옵션에 있는 ', '(콤마)가 찍히게 된다.
-- 4. 반복이 끝나면 close=")" 설정이 있으므로 ')' (닫힌 괄호)가 쓰인다.

--[배열(array)] 예시 1. 멤버 테이블에서 체크박스에 따라 권한별 보여주는 기능 만들기

--JSP
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

-- WHERE
-- WHERE절이 잘못된 예시.
<select id="getTest" resultType="board">

SELECT * FROM board

<if test="id != null">WHERE id = #{id} </if>
<if test="subject != null">AND subject = #{subject} </if>

</select>


-- WHERE절이 맞게 쓰인 경우
<select id="getTest" resultType="board">

    SELECT * FROM board

<WHERE>
<if test="id" != null> AND id = #{id} </if>
<if test="subject" != null> AND subject = #{subject} </if>
</WHERE>

</select>

-- 실제 쿼리
-- 1-1. "id" 칼럼 값만 있을 경우
SELECT * FROM board WHERE id = #{id}

-- 1-2. "subject" 칼럼 값만 있을 경우
SELECT * FROM board WHERE subject = #{subject}

-- 1-3. 두 컬럼 모두 값이 있을 경우
SELECT * FROM board WHERE id = #{id} AND subject = #{subject}

-- -> #WHERE AND 가 되지 않고 알아서 문법에 맞게 치환 해준다.


-- BIND 
-- 외부에서 전달된 파라미터를 이용하여 변수 생성하는 엘리먼트, 동적쿼리 변수를 생성할 때 사용한다.
<select | insert | update | delete>

<bind name="지정할 변수이름" value="파라미터 값+부가 옵션"/>

</select | insert | update | delete>

name 속성 : 자기가 지정할 변수 이름
value 속성 : 받아오는 파라미터 값 + 추가 문법 (이때 문법은 OGNL(Object Graph Navigation Language) 표현식을 사용한다.)

-- 실전 예시 1. Like 문 문법
Parameter 01 : id
Parameter 02 : subject

<select id="getTest" resultType="board">

SELECT * FROM board

<bind name="ids" value="'%' +id+ '%'"/>
<bind name="subjects" value="'%'+subject+'%'"/>

<where>
<if test="id != null"> AND id like #{ids}</if>
<if test="subject != null"> AND subject like #{subjects} </if>
</where>

</select>

-- 실전 예시 2. Map 사용 문법
<bind name="a" value="hMap.get('a', toString())"/>
<bind name="b" value="hMap.get('b', toString())"/>
<bind name="c" value="hMap.get('c', toString())"/>



-- SQL
-- 1.id 속성 값이 필수입니다.
-- 2.사용하려는 태그보다 먼저 선언되어야 합니다.(위에 존재해야 합니다.)

-- 문법
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>


-- INCLUDE
-- 개념
<-- sql> 문을 DML(Data Manipulation Language) 태그에 삽입하는 기술

-- 문법
<select,insert,update,delete>

<include refid="<sql> id"><property name="<sql> property" value=""/></include>

</select,insert,update,delete>
 
-- mapper에서 쓰인

-- <update id="appTest">
-- update ~ set ~ 이 구문이
-- </update>

-- controller에 와서
-- int a = service.appTest(~) 
-- a.put("success", (a==1));


-- 이런식으로 오는 것을 발견했다.
-- 아니, 리턴값이 없는데 갑자기 int...?
-- 찍어보니 1을 리턴하는 걸 보고 읭했다.

-- 찾아보니 insert, update, delete에는 resultType이 없고
-- row의 개수를 반환한다고 한다.
 
-- insert의 경우는 삽입된 행의 개수를 반환
-- update의 경우는 수정에 성공한 행의 개수를 반환(실패시 0 반환)
-- delete의 경우는 삭제한 행의 개수를 반환

-- 즉, 저 구문은 update 성공시에 웬만하면 1을 반환하고
-- 실패시는 0이므로 put에 success를 넣은 것

-- <sql> + <include><property> 설명

//no property 
<sql id="example01">
  FROM
</sql>

//property 한개 작성
<sql id="example02">
  FROM ${alias(별칭)}
</sql>

//property 여러 개 작성
<sql id="example03">
  FROM ${alias(별칭)} WHERE id = ${alias02(별칭)}
</sql>
<select id="selectUser" resultType="User">
SELECT id,name
<include refid="example03">
  <property name="alias" value="tablename"/>
  <property name="alias02" value="119"/>
</include>
</select>

--실행될 쿼리
SELECT id,name FROM tablename WHERE id = 119

-- 설명
/*

0.<sql> 문에는 parameter를 넘길 수 없으므로 property를 사용한다. ex) ${alias},${tablename}..
1. <sql> id 속성 == <include> refid 속성
2. <sql> ${alias(별칭)} == <property> name 속성  
3. <property> value 속성 : ${alias}에 들어갈 값

*/