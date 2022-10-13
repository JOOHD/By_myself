import java.util.Date;

public class BoardDTO {

    //  Field
    private int bno;            // 게시물 번호
    private String title;       // 게시물 제목
    private String content;     // 게시물 내용
    private String writer;      // 게시물 작성자
    private Date regdate;       // 게시물 작성일지
    private int viewcnt;        // 게시물 조회수

    //  Constructor
    public BoardDTO() {}

    public BoardDTO(int bno, String title, String content, String writer, Date regdate, int viewcnt) {
        this.bno = bno;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regdate = regdate;
        this.viewcnt = viewcnt;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public int getViewcnt() {
        return viewcnt;
    }

    public void setViewcnt(int viewcnt) {
        this.viewcnt = viewcnt;
    }

    @Override
    public String toString() {
        return "BoardDTO [bno=" + bno + ", content=" + content + ", regdate=" + regdate + ", title=" + title
                + ", viewcnt=" + viewcnt + ", writer=" + writer + "]";
    }

    
    
    
