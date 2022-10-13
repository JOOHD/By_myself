-- MySQL
-- 상품 테이블 
create table vam_book(
    bookId int primary key auto_increment,
    bookName varchar(50)   not null,
    authorId int,
    publeYear Date not null,
    publisher varchar(70) not null,
    cateCode varchar(30),
    bookPrice int not null,
    bookStock int not null,
    bookDiscount decimal(2,2),
    bookIntro text,
    bookContents text,
    regDate timestamp default now(),
    updateDate timestamp default now()
);