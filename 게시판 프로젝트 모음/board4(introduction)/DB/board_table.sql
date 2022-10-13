-- 게시판 테이블 만들기
CREATE TABLE board (
    bno number not null primary key -- 게시물 번호
    ,title varchar2(200) not null   -- 게시물 제목
    ,content varchar2(4000)         -- 게시물 내용
    ,writer varchar2(50) not null   -- 게시물 작성자
    ,regdate date default sysdate   -- 게시물 작성일자
    ,viewcnt number default 0       -- 게시물 조회수
);

-- 테이블 데이터 삭제
DELETE FROM board;

-- 게시물 레코드 1000개 삽입하기
declare
i number := 1; begin
while i <= 1000 loop
insert into board (bno, title, content, writer) values
((select nvl(max(bno)+1, 1) from board) ,'제목' || i, '내용' || i, 'kim');
i := i+1;
end loop; end;
/
select count(*) from board; select * from board;
--commit
commit

-- CRUD 만들기 - SQL문 쿼리 작성하기 (여기선 게시판 목록 불러오는 쿼리만 작성)
select bno, title, writer, regdate, viewcnt from board order by bno desc

-- CRUD Create - 게시글 작성하는 기능을 구현 
-- '글제목', '내용', '작성자' 부분을 변수 처리하면 되겠다.
insert into board (bno, title, content, writer) values ((select nvl(max(bno)+1, 1) from board), '글제목', '내용', '작성자')