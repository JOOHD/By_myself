import java.util.Arrays;

/*
  페이징 쿼리를 동적 제어하기 위해 필요한 데이터 'pageNum', 'amount'을 같이 파라미터로 전달하기 위한 용도로
  Criteria 클래스를 작성한다. 각각의 데이터를 분리하여 파라미터로 전달해도 되지만 연관성 있는 데이터를 같이 관리함으로써
  관리하기 편하고 재사용성에도 장점 때문에 클래스를 작성하였다. Criteria는 '기준'이라는 뜻인데 '검색의 기준'
  이라는 의미에서 클래스명으로 사용하였다.
 */

public class Criteria {

    /* 현재 페이지 */
    private int pageNum;

    /* 한 페이지 당 보여질 게시물 개수 */
    private int amount;

    /* 검색어 키워드 */
    private String keyword;

    /* 검색 타입 
       뷰로부터 사용자가 어떠한 주제로 검색하는지를 알기 위해서 Criteria 클래스에 'type'변수를 추가합니다.
    */
    private String type;

    /* 검색 타입 배열 
        type변수에 담길 데이터는 "T"(제목), "C"(내용), "W"(작성자), "TC"(제목 + 내용), "TW"(제목 + 작성자), "TCW"(제목 + 내용 + 작성자)입니다. 
        그런데 제가 사용할 방식에서는 "T", "C", "W"와 같이 문자 하나가 저장된 데이터가 필요로 합니다. 
        따라서 "TC", "TW", "TCW" 데이터들이 문자 하나씩 저장된 데이터가 될 수 있도록 배열로 변환해주는 작업을 해줍니다.
    */
    private String[] typeArr;

    /* 스킵 할 게시물 수 ( (pageNum -1) * amount ) */
    private int skip;

    /* 기본 생성자 -> 기본 세팅 : pageNum = 1, amount = 10 */
    public Criteria() {
        this.(1,10);
        this.skip = 0;
    }

    /* 생성자 => 원하는 pageNum, 원하는 amount */
    public Criteria(int pageNum, int amount {
        this.pageNum = pageNum;
        this.amount = amount;
        this.skip = (pageNum-1) * amount;
    }

    /*
      set() 메소드를 호출한다는 것은 amount, pageNum 값을 변경한다는 것을 의미한다. 
     */
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
		
		this.skip= (pageNum-1)*this.amount;
		
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		
		this.skip= (this.pageNum-1)*amount;
		
		this.amount = amount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
        /*
          type 변수에 데이터가 들어왔을 때 자동으로 배열 형식으로 변환하여 typeArr 변수에 저장될 수 있도록
          setType() 메서드를 수정해주다. (배열로 변환하기 위해서 String 타입의 데이터를 String 배열 타입 데이터로
          변환해주는 split() 메서드를 사용.) 
         */
		this.typeArr = type.split("");
	}

	public String[] getTypeArr() {
		return typeArr;
	}

	public void setTypeArr(String[] typeArr) {
		this.typeArr = typeArr;
	}

    @Override
    public String toString() {
        return "Criteria [amount=" + amount + ", keyword=" + keyword + ", pageNum=" + pageNum + ", skip=" + skip
                + ", type=" + type + ", typeArr=" + Arrays.toString(typeArr) + "]";
    }

    
}
