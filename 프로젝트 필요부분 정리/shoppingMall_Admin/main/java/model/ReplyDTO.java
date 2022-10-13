package main.java.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReplyDTO {

	private int replyId;
	
	private int bookId;
	
	private String memberId;

	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")	
	private Date regDate;
	
	private String content;
	
	private double rating;
    
}
