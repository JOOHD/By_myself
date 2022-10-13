create table vam_board(CHECK

    bno int auto_increament,         -- 번호(새로운 행 작성 시, 자동으로 +1)
    title varchar(150),              -- 제목
    content varchar(2000),           -- 내용
    writer varchar(50),              -- 작성자
    regdate timestamp default now(), -- 등록일
    updatedate date default now(),   -- 수정날짜
    constraint pk_board PRIMARY key(bno)

);

-- 작가 등록 
insert into vam_board(title, content, writer) values ('테스트 제목', '테스트 내용', '작가');

-- 게시판 목록
select * from vam_board;

-- 게시판 조회
select * from vam_board where bno = 8;

-- 게시판 수정
--  어느 행을 수정할지 지정 해주는 '게시판 번호(bno)', 수정이 될 대상인 '게시판 제목(title)', '게시판 내용(content)' 3개의 데이터 수정
update vam_board set title='제목 수정', content='내용 수정', updateDate = sysdate where bno = 8;
update vam_board set title='제목 수정', content='내용 수정', updateDate = now() where bno = 8;

-- 게시판 삭제
delete from vam_board where bno = '삭제할 게시판 번호';

-- 재귀 복사
insert into vam_board(title, content, writer)(select title,content,writer from vam_board);

-- 행 확인
select count(*) from vam_baord;

-- rownum Oracle에서 오라클에서 조회된 행이 몇번째 행인지 부여해주는 것. 단순히 select 문에 키워드를 넣기만 하면 된다.
-- Oracle 방식1
select rn, bno, title, content, regdate, updatedate from(
    select /* +INDEX_DESC(vam_board) */ rownum as rn, bno, title, content, writer, regdate, updatedate 
    from vam_board)
  --select rownum as rownum as rn, bno, title, content, writer, regdate, updatedate from vam_board order by bno desc
where rn between 11 and 20;
    --rn > 10 and rn <= 20;

    --서브 쿼리
    select /*+INDEX_DESC(vam_board pk_board) */ rownum as rn, bno, title, writer, regdate, updatedate from vam_board;

-- Oracle 방식2
select rn, bno, title, content, writer, regdate, updatedate from(
    SELECT /* INDEX_DESC(vam_boardk pk_board) */ rownum as rn, bno,title, content, writer, regdate, updatedate
    from vam_board where rownum <= 20)
where rn > 10;

-- 이번 방식은 'Rownm' 방식1과 큰 틀에서는 비슷, 이번 방식도 서브 쿼리의 실행 결과인 행 번호를 가진 테이블을 반환 받은 후 을 다시 rownum으로 필터링
-- 하지만 조금 다른 점은 rownum 필터링에 대한 일부를 서브 쿼리에서 진행하는 점.
-- 속도 면에서는 앞에 행을 검색할 때는 방식2가 더 빠르다 뒷번호는 비슷.

-- MySQL 경우 '키워드'와 '함수'를 제공하지 않기 때문에 변수를 사용하여 구현해야한다.
-- from 구문에서 rownum 변수의 값을 0으로 초기화하고, select 구문에서 rownum 변수의 값이 +1씩 추가 되도록
-- MySQL
select * from
        (select @rownum := @rownum + 1 as rn, bno, title, writer, regdate, updatedate
        from vam_board, (select @rownum := 0) as rowcolumn orderby bno desc) as rownum_table
where rn > 10 and rn <=20;
-- where rn between 10 and 20;

-- LIMIT 방식을 활용하여 bno 기준 역순으로 정렬된 테이블에서 11번째 행과 20번째 행을 출력하는 쿼리
select bno, title, writer, regdate, updatedate
from vam_board order by bno desc
limit 10, 10;
-- 위의 10, 10은 <skip><count>를 의미 10개행 skip하고 10개(count)행을 선택한다는 의미.
-- ex) 51~60 = 'limit 50, 10', 301~310 'limit 300,10'

--------------------------------------------------------------------------------------------------------------
-- 첫 번째는 현재 페이지(pageNum)에 대한 정보이고 두 번째는 하나의 페이지에 몇 개의 목록(amount)을 보여줄 것인지에 대한 정보
-- Oracle 방식2
select rn, bno, title, content, writer, regdate, updatedate from(
    SELECT /* INDEX_DESC(vam_boardk pk_board) */ rownum as rn, bno,title, content, writer, regdate, updatedate
    from vam_board where rownum <= 20) /* pageNum * amount */
where rn > 10; /* 10 = (pageNum -1) * amount */

-- LIMIT 방식을 활용하여 bno 기준 역순으로 정렬된 테이블에서 11번째 행과 20번째 행을 출력하는 쿼리
select bno, title, writer, regdate, updatedate
from vam_board order by bno desc
limit 10, 10; /* 앞(pageNum -1 * amount), 뒤 amount */


-- 주제별 검색(Oracle)
select bno, title, content, writer, regdate, updatedate from(
        
    select /*+INDEX_DESC(vam_board pk_board) */ rownum  as rn, bno, title, content, writer, regdate, updatedate
    
    from vam_board where rownum <= 10 and title like '%검색%')
                
where rn > 0;

    -- 제목
    title like '%검색%'
    -- 내용
    content like '%검색%'
    -- 작성자
    writer like '%검색%'    
    -- 제목 + 내용
    (title like '%검색%' OR content like '%검색%')
    -- 내용 + 작성자
    (content like '%검색%' OR writer like '%검색%')
    -- 제목 + 내용 + 작성자
    (title like '%검색%' OR content like '%검색%' OR writer like '%검색%')

	-- 소괄호를 붙여준 이유는 where문에소 and 연산자가 사용되고 있는데 이는 OR연산자보다 우선 순위가 높아서 의도와 다르게 동작하는 것을 예방

-- 주제별 검색(MySQL)
select * from (
		select bno, title, writer, regdate, updatedate  
    	from vam_board where title like '%검색%'
    	order by bno desc) as T1
limit 10, 10;

	-- 제목
    title like '%검색%'
    -- 내용
    content like '%검색%'
    -- 작성자
    writer like '%검색%'    
    -- 제목 + 내용
    title like '%검색%' OR content like '%검색%'
    -- 내용 + 작성
    content like '%검색%' OR writer like '%검색%'
    -- 제목 + 내용 + 작성자
    title like '%검색%' OR content like '%검색%' OR writer like '%검색%'

	-- MySQL 쿼리문에서는 조건문에 AND 연산자가 없어서 소괄호를 붙여주지 않는다.