public class PageMaker {

    private Criteira cri;
    private int totalCount;         // 총 게시글 수
    private int startPage;
    private int endPage;
    private boolean prev;           // 이전 버튼
    private boolean next;           // 다음 버튼
    private int displayPageNum = 5; // 화면 하단에 포여지는 페이지 버튼의 수 (버튼 수 설정 가능)

    /*
      PageMaker 객체를 사용하려면 setCri() 와 setTotalCount()를 먼저 호출해서 값을 셋팅해야 한다.
      페이징 버튼들의 값을 구하려면 제일 먼저 총 게시글 수가 있어야 위의 계산식대로 차례로 구할 수 있따.
      그렇기에 총 게시글을 셋팅할 때 계산식 메소드를 호출하게 한 것이다. 또한 Criteria 객체에서 필요한 
      page와 perPageNum을 사용하기 위해서 setCri()를 먼저 셋팅해야 한다. 컨트롤러에서 객체로 값을 셋팅할 때 유의 
     */

    public Criteria getCri() {
        return cri;
    }

    public void setCri(Criteria cri) {
        this.cri = cri;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount; 
        calcDate(); // 총 게시글 수를 셋팅할때 calDate() 메서드를 호출하여 페이징 관련 버튼 계산을 한다.
    }

    // 페이징의 버튼들을 생성하는 계산식을 만들었다. 끝 페이지 번호, 이전, 다음 버튼들을 구한다.
    private void calcDate() {

        /*
          Criteria cri.getPage() : 현재 페이지 번호
          Criteria cri.getPerPageNum() : 한 페이지당 보여줄 게시글의 갯수
          int totalCount : 총 게시글 수 
          int endPage : 화면에 보여질 마지막 페이지 번호, 끝 페이지 번호
         */

        // 끝 페이지 번호 = (현재 페이지 번호 / 화면에 보여질 페이지 번호의 갯수) * 화면에 보여질 페이지 번호의 갯수
        endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);

        /*
          시작 페이지 번호를 구할 때, 마지막 페이지 번호가 화면에 보여질 페이징 버튼의 갯수보다 작으면 문제가 생긴다.
          시작 페이지 번호가 음수가 되어버리는 상황이 발생한다. 예를들면 끝 페이지의 번호가 3이고, 보여줄 페이지 갯수가 5라면,
          시작 페이지 번호는 -1이 된다.따라서, 구한 시작페이지 번호가 0보다 작으면(음수) 시작 페이지를 1로 해주는 로직을 추가해야 한다. 
         */

        // 시작 페이지 번호 = (끝 페이지 번호 - 화면에 보여질 페이지 번호의 갯수) + 1
        startPage = (endPage - displayPageNum) + 1; // (+1을 해도 음수가 나오면)
        if(startPage <= 0) startPage = 1;

        /*  
            마지막 페이지의 번호를 구한 뒤, 끝 페이지 번호보다 작은 경우에 마지막 페이지의 번호를 끝 페이지 번호로 저장
            화면에 보여질 끝 페이지 번호는 마지막 페이지의 번호보다 클 수는 없다. 그렇기 때문에 위와 같은 조건을 넣어줘야 한다.
            이 부분은 시작 페이지 번호까지 구한 뒤에 처리해줘야 한다.
        */

        //  마지막 페이지 번호  = 총 게시글 수 / 한 페이지당 보여줄 게시글의 갯수
        int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
        if(endPage > tempEndPage) {
           endPage = tempEndPage;
        }

        // 이전 버튼 생성 여부 = 끝 페이지 번호 * 한 페이지당 보여줄 게시글의 갯수 < 총 게시글의 갯수 ? true : false
        prev = startPage == 1 ? false : true;
        // 다음 버튼 생성 여부 = 끝 페이지 번호 *
        next = endPage * cri.getPerPageNum() < totalCount ? true : false;
    }

    /*  UriComponents Class
        이 클래스는 URI를 생성할 때 도움이 되는 Spring에서 제공해주는 클래스이다. 쿼리 문자열을 추가해줘서 원하는 URI를 만들 수 있다.
        쿼리 문자열은 /board/boardList?IDX=77&page=3&perPageNum=10 주소 URI을 보면 ? 뒤에 오는 것들을 말한다.
        uriComponents를 사용하게 되면 /board/boardDetail${pageMaker.makeQueryPage(page)} 이런식으로 사용이 가능하다.
     */
    public String makeQueryPage(int page) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("perPageNum", cri.getPerPageNum())
                .build();
        return uri.toUriString();
    }

    public String makeQueryPage(int idx, int page) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .queryParam("idx", idx)
                .queryParam("page", page)
                .build();
        return uri.toUriString();
    }

    public void setCri(Criteira cri) {
        this.cri = cri;
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

    public int getDisplayPageNum() {
        return displayPageNum;
    }

    public void setDisplayPageNum(int displayPageNum) {
        this.displayPageNum = displayPageNum;
    }

    
}
