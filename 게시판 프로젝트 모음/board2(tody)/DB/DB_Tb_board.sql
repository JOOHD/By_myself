CREATE TABLE `tb_board` (
 `IDX` int(11) NOT NULL AUTO_INCREMENT,
 `PRE_IDX` int(11) DEFAULT NULL,
 `TITLE` varchar(100) NOT NULL,
 `CONTENTS` varchar(4000) NOT NULL,
 `HIT_CNT` int(11) NOT NULL DEFAULT '0',
 `DEL_CHK` varchar(1) NOT NULL DEFAULT 'N',
 `CREA_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 `CREA_ID` varchar(30) NOT NULL,
PRIMARY KEY (`IDX`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8

- 07행: delete 여부를 알 수 있는 컬럼이다.
- 08행: default를 수정했다.
    이전의 DB를 생성했을 때 작성일(CREA_DATE) 컬럼에서 DEFAULT를 UPDATE ON CURRENT_TIMESTAMP
    이렇게 설정했었다. 이렇게 해 놓으면 글 그대로 업데이트 될 때마다 수정한 시각이 기록된다.
    수정한 작성일을 기록하고 싶으면 그대로 둬도 된다. 나는 수정을 하든 말든 작성한 날을 기록하고 싶기 
    때문에 DEFAULT를 다음과 같이 바꾸었다.
