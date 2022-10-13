package main.java.model;

import java.util.Arrays;

public class Criteria {
    
    /* 현재 페이지 번호 */
	private int pageNum;
	
	/* 페이지 표시 개수 */
	private int amount;
	
	/* 페이지 skip */
	private int skip;
	
	/* 검색 타입 */
	private String type;
	
	/* 검색 키워드 */
	private String keyword;
	
	/* 작가 리스트 */
	private String[] authorArr;
	
	/* 카테고리 코드 */
	// 사용자가 요청하는 카테고리 번호를 저장하기 위함.
	private String cateCode;	
	
	/* 상품 번호(댓글 기능에서 사용) */
	private int bookId;	
	
	/* Criteria 생성자 */
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.skip = (pageNum -1) * amount;
	}
	
	/* Criteria 기본 생성자 */
	public Criteria(){
		this(1,10);
	}
	
	/* 검색 타입 데이터 배열 변환 : 이름은 동명이인으로 인해 반환타입이 int 보다는 String 이 낫다.
	 	type 변수에 담긴 값을 MyBatis의 Mapper에 그대로 전달하면 활용을 할 수 가 없다.
		활용을 하기 위해선 "T", "C","G" 하나하나의 String 값이 필요로 한다. 따라서 뷰로부터 전달받은
		type의 값을 String 클래스의 split() 메서드를 사용하여 String 배열 형태로 Mapper에 전달하게 된다.

		 그렇다면 typeArr 변수와 Getter/Setter 메서드를 작성하지 않고 Getter 메서드에 해당하는 
		 getTypeArr() 메서드만 작성했는지 의아해하실 수도 있습니다. 
		 그 이유는 MyBatis에서 값을 가져올 때 Getter메서드의 형태로 필요로 한 값을 가져오기 때문입니다. 
		 type 변수에 저장된 값을 단지 배열 형태로 가져만 오면 돼서 
		 typeArr 변수와 Setter 메서드를 따로 사용할 상황이 없기 때문에 Getter 메서드만 작성해주었습니다.
	*/
	public String[] getTypeArr() {
		return type == null? new String[] {}:type.split("");
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.skip = (pageNum - 1) * this.amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
		this.skip = (this.pageNum - 1) * amount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String[] getAuthorArr() {
		return authorArr;
	}

	public void setAuthorArr(String[] authorArr) {
		this.authorArr = authorArr;
	}

	public String getCateCode() {
		return cateCode;
	}

	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + ", type=" + type
				+ ", keyword=" + keyword + ", authorArr=" + Arrays.toString(authorArr) + ", cateCode=" + cateCode
				+ ", bookId=" + bookId + "]";
	}

}
