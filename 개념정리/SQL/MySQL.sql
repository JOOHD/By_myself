-- 데이터 베이스 생성
CREATE DATABASE [database name] CHARACTER SET [character set];

-- 데이터베에스 선택
USE [database name];

-- 데이터베이스 삭제
DROP DATABASE [database name];

-- 테이블 생성
CREATE TABLE User
(
    ID INT,
    Name VARCHAR(30),
    BirthDay DATE,
    Age INT
);

-- MySQL에서 키워드와 구문, 문자열은 대소문자를 구분하지 않는다.
-- Mysql에서 테이블 명과 필드의 이름은 대소문자를 구분한다.

-- VARCHAR(크기) : 지정한 크기 이하로만 컬럼에 값 삽입 가능
-- CHAR(크기) : 지정할 크기를 다 채우지 않았을 경우 빈 값을 공백으로 채움
-- INT(정수), DATETIME(날짜 타입의 값)
-- PK(PRIMARY KEY) : 중복이나 빈값(NULL)이 들어올 수 없음
-- NN(NOT NULL) : Not Null 못들어옴
-- UQ(UNIQUE) : 중복 값을 넣을 수 없음
-- B : 데이터를 이진 문자열로 저장함(010101 같은)
-- UN : Unsigned data type (- 범위 삭제)
-- ZF : Zero Filled 컬럼 크기보다 작은 값을 넣었을 경우 0으로 채운 뒤 삽입시킴
-- AI : Insert 시마다 값 1씩 늘어남
-- G : 다른 열을 기반으로 한 수식으로 생성된 값
-- Default/Expression : 기본값, 기본값에 수식 설정

-- 제약조건 확인
DESC 스키마명.테이블명;

-- PK
ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 PRIMARY KEY(NAME);
-- FK
ALTER TABLE 티이블명 ADD CONSTRAINT 제약조건이름 FOREIGN KEY(컬럼명);
    REFERENCES PK테이블명(PK컬럼명) (ON DELETE CASCADE / ON UPDATE CASCADE);
-- NOT NULL
ALTER TABLE 테이블명 MODIFY COLUMN 컬럼명 데이터타입(크기) NOT NULL;
-- UNIQUE 
ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 UNIQUE(NAME);
-- B(Binary)
ALTER TABLE 테이블명 CHANGE 컬럼명 데이터타입(크기) BINARY;
-- UN(Unsigned Data type)
ALTER TABLE 테이블명 MODIFY 컬럼명 데이터타입(크기) UNSIGNED;
-- ZF(Zero Filled)
ALTER TABLE 테이블명 MODIFY 컬러명 데이터타입(크기) ZEROFILL;
-- AI
ALTER TABLE 테이블명 MODIFY 컬러명 데이터타입(크기) AUTO_INCREMENT;
-- G(Generated Column)
ALTER TABLE 테이블명 MODIFY COLUMN 컬럼명 데이터타입(크기) GENERATED ALWAYS AS (NAME + 'A') STORED;
-- Default
ALTER TABLE 테이블명 MODIFY 컬럼명 데이터타입(크기) default '값';
-- 테이블 제약조건 초기화
ALTER TABLE 테이블명 DROP INDEX 제약조건명;
-- 컬럼 제약조건 초기화
ALTER TABLE 테이블명 CHANGE COLUMN NAME 컬럼명 데이터타입(크기) NULL;

/*
  조회(조회 시 컬럼명을 사용하면 입력한 컬럼만 조회가능, *를 사용하면 테이블의 전체 컬럼 조회)
  조건이 필요하면 WHERE, CROUP BY를 사용할 수 있고, ORDER BY를 통해 원하는 순으로 정렬이 가능하다.
*/
SELECT 컬럼명 OR * FROM 테이블명
-- 추가
INSERT INTO 테이블명(컬럼명) VALUES(값)
-- 변경 WHERE가 필수가 아니지만 WHERE을 사용하지 않으면 UPDATE 시 테이블의 모든 컬럼이 변경되기 때문에 WHERE을 꼭 써주
UPDATE 테이블명 SET 컬럼명 = 값 WHERE 조건
-- 삭제 변경과 동일하게 WHERE을 꼭 사용해줘야한다. WHERE을 넣지 않으면 테이블의 모든 데이터들이 삭제가 되기 때문이다.
DELETE FROM 테이블명 WHERE 조건

--EX)
    -- 조회
    SELECT NAME, AGE FROM TB1;
    -- 삽입
    INSERT INTO TB1(NAME, AGE) VALUES('철수', 11);
    -- 수정
    UPDATE TB1 SET NAME='영희' WHERE NAME='철수';
    -- 삭제
    DELETE FROM TB1 WHERE NAME='영희';

/* 
  ON DUPLICATE KEY(중복제거)
  INSERT 시 KEY 컬럼에 동일한 값이 존재하면 해당 KEY에 해당하는 ROW에 INSERT가 아닌 UPDATE가 돌아가게 된다.
*/
-- EX)
    INSERT INTO TB1(컬럼명) VALUES (값)
    ON DUPLICATE KEY -- PK 컬럼명에 중복이 들어올 시
    UPDATE 컬럼명 = 값; -- 중복 컬럼의 ROW를 UPDATE 

    INSERT INTO TB1(NAME, AGE) values ('철수', 20)
    ON DUPLICATE KEY
    UPDATE NAME = '중복', AGE = 99;

SELECT * FROM TB1; -- 조회용

-- 테이블 삭제
DROP TABLE [database name];

-- 테이블에 필드(열) 추가
ALTER TABLE [table name] ADD [column name] [datatype];
ALTER TABLE User ADD PhoneNumber INT;

-- 테이블 필드(열) 타입 변경
ALTER TABLE [table name] MODIFY COLUMN [column name] [datatype];
ALTER TABLE User MODIFY ID VARCHAR(20);

-- 테이블 필드(열) 삭제
ALTER TABLE [table name] DROP [column name];
ALTER TABLE User DROP Age;

-- 테이블에 레코드(행) 추가
INSERT INTO [table name] VALUE (value1, value2, value3....);
INSERT INTO [ID, Name, BirthDay] VALUES(1, '김태하', '1992-11-04');

-- 테이블의 레코드(행) 내용 수정
UPDATE [table] SET [column] [value] WHERE [condition];
UPDATE User SET Age = 30 WHERE Name = '김태하';

-- 테이블의 레코드(행) 삭제
DELETE FROM [table] WHERE [condition];
DELETE FROM User WHERE Name = '김태하';

Join
-- join은 두 개의 table들을 연결(join)해서 두 table의 레코드를 읽어 들이고 싶을 때 사용

INNER JOIN
-- 기준이 되는 테이블(left table)과 join이 걸리는 테이블(right table) 양쪽 모두에 matching되는 row만 select가 됨.

LEFT JOIN
-- 기준이 되는 테이블(left table)의 모든 row와join이 걸리는 테이블(right table)중 left table과 matching되는 row만 select가 됨

RIGHT JOIN
-- join이 걸리는 테이블(right table)의 모든 row와 기준의 되는 테이블 (left table)에서 right table과 matching되는 row만 select가 됨.

FULL JOIN
-- 기준이 되는 모든 테이블의 모든 row를 select 한다.

Join 기본 문법
SELECT
테이블이름.조회할 테이블,
테이블이름.조회할 테이블,
FROM 기준테이블 이름
(INNER, LEFT, RIGHT FULL) JOIN 조인테이블 이름
ON 기준테이블이름.기준키 = 조인테이블이름.기준키;

ex
SELECT users.id, users.name, users.age, users.gender, account.account_type
FROM users JOIN accounts ON accounts.id = users.account_id;

WITH TBL1 AS -- 가상 테이블
            (
              SELECT '철수' AS NAME
              UNION ALL
              SELECT '영희' AS NAME
            ),
    TBL2 AS 
            (
              SELECT '영희' AS NAME
              UNION ALL
              SELECT '희영' AS NAME
            )

-- INNER JOIN (같은 항목만 조회)
SELECT A.NAME
  FROM TBL1 A INNER JOIN TBL2 B
    ON A.NAME = B.NAME;
    
-- LEFT, RIGHT OUTER JOIN (한쪽에만 값이 있어도 출력)
SELECT A.NAME, B.NAME
  FROM TBL1 A LEFT OUTER JOIN TBL2 B
    ON A.NAME = B.NAME;
    
SELECT A.NAME, B.NAME
  FROM TBL1 A RIGHT OUTER JOIN TBL2 B
    ON A.NAME = B.NAME;  

-- CROSS JOIN (모든 경우의 수가 나오게)
SELECT A.NAME, B.NAME
  FROM TBL1 A CROSS JOIN TBL2 B;

문제1
CREATE TABLE Reservation(ID INT, Name VARCHAR(30), ReserveDate DATE, RoomNum INT);
CREATE TABLE Customer (ID INT, Name VARCHAR(30), Age INT, Address VARCHAR(20));

INSERT INTO Reservation(ID, Name, ReserveDate, RoomNum) VALUES(1, '홍길동', '2016-01-05', 2014);
INSERT INTO Reservatino(ID, Name, ReservaDate, RoomNum) VALUES(2, '임꺽정', '2016-02-12', 918);
INSERT INTO Reservation(ID, Name, ReserveDate, RoomNum) VALUES(3, '장길산', '2016-01-16', 1208);
INSERT INTO Reservation(ID, Name, ReserveDate, RoomNum) VALUES(4, '홍길동', '2-16-03-17', 504);

INSERT INTO Customer (ID, Name, Age, Address) VALUES (1, '전우치', 17, '수원');
INSERT INTO Customer (ID, Name, Age, Address) VALUES (2, '임꺽정', 11, '인천');
INSERT INTO Customer (ID, Name, Age, Address) VALUES (3, '장길산', 13, '서울');
INSERT INTO Customer (ID, Name, Age, Address) VALUES (4, '홍길동', 17, '서울');

문제2
-- 테이블 특성 및 요소 변경
ALTER TABLE PLAYER
ADD (ADDRESS VARCHAR(30));          -- 칼럼 추가

ALTER TABLE PLAYER
DROP COLUMN ADDRESS;                -- 칼럼 삭제

ALTER TABLE TEAM_TEMP
MODIFY (ORIG_YYYY VARCHAR(8) DEFAULT '20200129' NOT NULL); -- 컬럼 수정

ALTER TABLE PLAYER
RENAME COLUMN PLAYER_ID TO TEAM_IDL -- 컬럼명 변경

-- 테이블 내 컬럼 추가
INSERT INTO Reservation(ID, Name, ReserveDate, RoomNum)
VALUES(1, '홍길동', '2016-01-05', 2014);

-- UPDATE(테이블 또는 뷰의 데이터를 갱신)
UPDATE EMPLOYEE                         -- 수정 할 테이블
    SET WORKDEPT = 'D11',               -- 수정 할 내용
        PHONENO = '7213',           
        JOB = 'DESIGNER'
    WHERE EMPNO = '000270'              -- 수정 할 위치

-- DELETE(테이블에서 행을 제거) 
DELETE FROM CORPDATA.EMPLOYEE

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

-- SELECT(테이블에 있는 데이터들 조히)
SELECT Nm_Kor,Age FROM My_Table WHERE Age=25

-- CREATE INDEX(인덱스 생성)
CREATE INDEX grade_index_score
ON student.grade (score ASC)

-- DROP INDEX (인덱스 삭제)
ALTER TABLE Reservation
DROP INDEX NameIdx;

-- Reservation 테이블에서 Name 필드와 RoomNum 필드만을 선택하는 예제
SELECT Name, RoomNum FROM Reservatino;

-- 선택한 결과의 정렬 ASC(오름차순) DESC(내림차순)
SELECT * FROM Reservation ORDER BY ReserveDate;
SELECT * FROM Reservation ORDER BY ReserveDate DESC, RoomNum ASC;

-- Name 필드의 값이 '홍길동'인 레코드만을 선택하는 예제
SELECT * FROM Reservation WHERE Name = '홍길동';

-- ID값이 3이하이면서 ReserveDate 필드의 값이 2016년 2월 1일 이후
SELECT * FROM Reservation WHERE ID <= 3 AND ReservaDate > '2016-02-01';

-- alias = AS
SELECT 필드이름 AS 별칭 FROM 테이블이름;
SELECT 필드이름 FROM 테이블이름 AS 별칭;

SELECT ReserveDate, CONCAT(RoomNum, ":", Name) AS ReserveInfo FROM Reservation;

