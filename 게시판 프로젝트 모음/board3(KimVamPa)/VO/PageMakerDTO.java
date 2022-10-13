import com.vam.model.Criteria;

/*
  현재 페이지에 대한 정보인 'Criteria'와 게시물의 총개수인 'total'을 파라미터를 부여한 PageMakerDTO 생성자를 작성한다.
  해당 생성자는 전달받은 Criteria와 total 정보를 활용하여 계산 과정을 거친 후 PageMakerDTO의 변수에 대한 값을 초기화 한다. 
 */

public class PageMakerDTO {

    /* 시작 페이지 */
    private int startPage;                     // 화면에 표시되는 페이지 '시작 번호' 

    /* 끝 페이지 */
    private int endPage;                       // 화면에 표시되는 페이지 '끝 번호'

    /* 이전 페이지, 다음 페이지 존재 유무 */
    private boolean prev, next;                // 화면에 보이느 10개 페이지의 '이전 페이지', '다음 페이지'

    /* 전체 게시물 수 */
    private int total;                         // '전체 페이지' 해당 정보가 있어야 'startPage', 'endPage', 'prev', 'next'의 값을 구핤 수 있따.

    /* 현재 페이지, 페이지당 게시물 표시수 정보 */
    private Criteria cri;                      //  Criteria 클래스의 pageNum(현재 페이지) 변수 값을 얻기 위해 선언

    /* 생성자 
        작성한 생성자에선 다음의 순서대로 값들을 계산하여 각 변수를 초기화 하게 된다.
        1.화면에 보일 마지막 페이지
        2.화면에 보여질 시작 페이지
        3.전체 마지막 페이지 계산
        4.화면에 보여질 마지막 페이지 유효한지 체크
        5.화면에 보여질 페이지 이전페이지 존재 여부
        6.화면에 보여질 페이지 다음페이지 존재 여부
      
    */
    public PageMakerDTO(Criteria cri, int total) {

        this.cri = cri;
        this.total = total;

        /* 마지막 페이지 
           Math.ceil() 함수는 화면에 보여질 끝 번호를 구하기 위해서 사용 
           현재의 페이지를 10으로 나눈 후 그값을 올림하고, 다시 10을 곱해준다.
           ex) 현페 7 endPage 10, 현페 23 endPage 30

           (int)로 다시 형변환 해주는 이유는 Math.ceil() 메소드의 반환 타입이 double이기 때문이다.
        */
        this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;

        /* 시작 페이지 
           화면에 표시될 페이지 번호들은  10개이기 때문에 끝번호가 구해졌다면 거기서 9를 빼준다면 첫 번째 번호를 구할 수 있다. 
        */
        this.startPage = this.endPage - 9;

        /* 전체 마지막 페이지 
           현재 게시물의 총개수를 통해 전체 페이지의 마지막 페이지(realEnd) 값을 구하는 식이다.
           전체 페이지(total)를 화면에 표시될 게시물 수(amount)로 나눈 후 올림한다. 올림에는 Math.ceil() 메서드 사용
           total에 1.0을 곱해준 이유는 int형인 total과 int형인 amount를 나눌 경우 본래는 소수점이 나와야 하는 경우에도 실제로는 
           소수점을 없애버리고 정수만 리턴하기 때문이다. 따라서 1.0값을 total에 곱해줌으로써 double 타입으로 형변환 한 후,
           amount 값으로 나눈 결과 또한 형변환되어 값이 소수점으로 출력될 수 있도록 한다. 
        */
        int realEnd = (int)(Math.ceil(totla * 1.0/cri.getAmount()));

        /* 전체 마지막 페이지(realend)가 화면에 보이는 마지막페이지(endPage) 보다 작은 경우, 보이는 페이지(endPage) 값 조정 
          
           위의 코드가 실행되는 시점의 '화면에 표시될 마지막 페이지'인 endPage 값의 경우 무조건 10단위의 값이 들어 잇따.
           문제는 이럴 경우 실제 '전체 페이지 마지막 번호'인 realEnd 값보다 큰 경우가 발생한다. 예를 들어 실제 마지막 페이지는 
           7이 되어야 하지만 화면에 표시되는 마지막 페이지는 10인 경우가 발생한다.
           이러한 상황을 막기 위해서 if 조건문을 통하여 endPage 가 realEnd보다 큰 경우 endPage에 realend 값을 저장하도록 코드 작성
        */
        if(realEnd < this.endPage) {
            this.endPage = realEnd;
        }

        /* 시작 페이지 (startPage) 값이 1보다 큰 경우 true 
           'prev' 시작 펭지ㅣ가 1보다 크다면 존재하기 때문에 startPage>1 이면 true가 저장 되도록 한다.
                  화면에 1부터 10페이지가 보이는 경우를 제외하면 시작 페이지가 모두 10보다 크기 때문에 
                  그 이전 10개의 변호 페이지로 이동할 수 있는 '이전 페이지 버튼'이 존재해야 한다.      
        */
        this.prev = this.startPage > 1;

        /* 마지막 페이지 (endPage) 값이 1보다 큰 경우 true 
           'next' realEnd 보다 화면에 보이는 endPage 가 작다면 존재하기 때문에
           endPage < realEnd 이면 true가 저장되도록 한다. 
           ex) 화면에 보이는 마지막 페이지가 20인데 전체 페이지 마지막 번호가 27이면 다음 페이지 버튼이 존재해야 한다.
               반대로 화면에 보이는 마지막 페이지가 23인데 전체 페이지 마지막 번호가 23이면 이동할 수 있는 다음 10개의 페이지가
               존재하지 않기 때문에 '다음 페이지 버튼'이 없어야 한다.
        */
        this.next = this.endPage < realEnd;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Criteria getCri() {
        return cri;
    }

    public void setCri(Criteria cri) {
        this.cri = cri;
    }

    @Override
    public String toString() {
        return "PageMakerDTO [cri=" + cri + ", endPage=" + endPage + ", next=" + next + ", prev=" + prev
                + ", startPage=" + startPage + ", total=" + total + "]";
    }

    
}



