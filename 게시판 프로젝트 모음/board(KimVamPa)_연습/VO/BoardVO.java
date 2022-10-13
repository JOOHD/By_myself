package board3_Copy.VO;

import java.util.Date;

public class BoardVO {
	
	/*
    bno number generated always as IDENTITY,
    title varchar2(150) not null,
    content varchar2(2000) not null,
    writer varchar2(50) not null,
    regdate date default sysdate,
    updatedate date default sysdate
	*/

	/* 게시판 번호 */
	private int bno;
	
	/* 게시판 제목 */
	private String title;
	
	/* 게시판 내용 */
	private String content;
	
	/* 게시판 작가 */
	private String writer;
	
	/* 등록 날짜 */
	private Date regdate;
	
	/* 수정 날짜 */
	private Date updateDate;

}
