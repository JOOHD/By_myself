CREATE DATABASE toyblog;

CREATE TABLE board (

    id INT AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(64),
    context VARCHAR(512),
    attachments VARCHAR(64),
    likes INT,
    views INT,
    create_time DATETIME,
    update_time DATETIME
)

INSERT INTO board VALUES(1, '제목1', '내용1', '첨부없음', 0, 0, NOW(), NULL);