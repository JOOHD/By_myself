package Criteria;

public class Criteria {
    
    private int page; // 현재 페이지 번호
    private int perPageNum; // 한 페이지당 보여줄 게시글의 갯수

    public int getPageStart() { // 특정 페이지의 게시글 시작 번호, 게시글 시작 행 번호
    // (현재 페이지 번호 -1) * 페이지당 보여줄 게시글 갯수 
        return (this.page-1)*perPageNum;
    }

    /*
      최초로 게시판 목록에 들어 왔을 때를 위해서 기본 셋팅을 해야 한다. 왜냐하면 페이징을 처리하기
      위해선 현재 페이지 번호와 페이지당 게시글 수가 필요한데, 처음 게시판에 들어오게 되면 두 개의 정보를
      가져올 방법이 없기 때문에 기본 생성자를 통해 기본 값을 셋팅하도록 하자. 현재 페이지를 1페이지로,
      페이지당 보여줄 게시글의 수를 10개로 기본 셋팅해두었다. 

      setter : 잘못된 값들이 셋팅되지 않게 적절하게 set 메서드 셋팅
        - setPage() : 페이지가 음수값이 되지 않게 설정. 음수가 되면 1페이지를 나타낸다.
        - setPerPageNum() : 페이지당 보여줄 게시글 수가 변하지 않게 설정했다.

      getter : get 메서드를 셋팅
     */

    // 기본 생성자 셋팅
    public Criteria() {
        this.page = 1;
        this.perPageNum = 10;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int age) {
        if(page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
    }

    public int getPerPageNum() {
        return perPageNum;
    }

    public void setPerPageNum(int pageCount) {
        int cnt = this.perPageNum;
        if(pageCount != cnt) {
            this.perPageNum = cnt;
        } else {
            this.perPageNum = pageCount;
        }
    }

    
}
