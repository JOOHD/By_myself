public class PageMaker {
    
    private Criteira cri;
    private int totalCount;         // 총 게시글 수
    private int startPage;
    private int endPage;
    private boolean prev;           // 이전 버튼
    private boolean next;           // 다음 버튼
    private int displayPageNum = 5; // 화면 하단에 포여지는 페이지 버튼의 수 (버튼 수 설정 가능)

    public Criteira getCri() {
        return cri;
    }
    public void setCri(Criteira cri) {
        this.cri = cri;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcDate(); // 총 게시글 수를 셋팅할때 calDate() 메서드를 호출하여 페이징 관련 버튼을 계산을 한다.
    }

    private void calcDate() {
        /*
          Criteria cri.getPage() : 현재 페이지 번호
          Criteria cri.getPerPageNum() : 한 페이지당 보여줄 게시글의 갯수
          int totalCount : 총 게시글 수 
          int endPage : 화면에 보여질 마지막 페이지 번호, 끝 페이지 번호
         */

         // 끝 페이지 번호 = (현재 페이지 번호 / 화면에 보여질 페이지 번호의 개수) * 화면에 보여질 페이지 번호의 갯수
         endPage = (int) (math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);

         /*
          시작 페이지 번호를 구할 때, 마지막 페이지 번호가 화면에 보여질 페이징 버튼의 갯수보다 작으면 문제가 생긴다.
          시작 페이지 번호가 음수가 되어버리는 상황이 발생한다. 예를들면 끝 페이지의 번호가 3이고, 보여줄 페이지 갯수가 5라면,
          시작 페이지 번호는 -1이 된다.따라서, 구한 시작페이지 번호가 0보다 작으면(음수) 시작 페이지를 1로 해주는 로직을 추가해야 한다. 
         */

         // 시작 패이지 번호 = (끝 페이지 번호 - 화면에 보여질 페이지 번호의 갯수) + 1
         startPage = (endPage - displayPageNum) + 1; // (+1을 해도 음수가 나오면)
         if(startPage <= 0) startPage = 1;

         int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
         if(endPage > tempEndPage) {
             endPage = tempEndPage;
         }

        // 이전 버튼 생성 여부 = 끝 페이지 번호 * 한 페이지당 보여줄 게시글의 갯수 < 총 게시글의 갯수 ? true : false
        prev = startPage == 1 ? false : true;
        // 다음 버튼 생성 여부 = 끝 페이지 번호 * 한 페이지당 보여줄 게시글의 갯수 < 총 게시글의 갯수 ? true : false
        next = endPage * cri.getPerPageNum() < totalCount ? true : false;
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
