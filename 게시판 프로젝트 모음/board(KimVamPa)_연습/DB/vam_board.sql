CREATE TABLE vam_board(CHECK

    bno int auto_increament,         -- 번호(새로운 행 작성 시, 자동으로 +1)
    title varchar(150),              -- 제목
    content varchar(2000),           -- 내용
    writer varchar(50),              -- 작가
    regdate timestamp default now(), -- 등록일
    updatedate date default now(),   -- 수정날짜
    constraint pk_board PRIMARY key(bno)

);

