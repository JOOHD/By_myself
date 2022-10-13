import com.vam.model.BoardVO;

public interface BoardService {
    
    /* 게시판 등록 */
    public void enroll(BoardVO board);

    /* 게시판 목록 
        게시판 목록' 쿼리 수행을 호출하는 메서드를 추가합니다. 
        해당메서드도 게시판 목록 데이터를 반환받아야 하기 때문에 리턴 타입은 List <BoardVO>입니다.
    */
    public List<BoardVO> getList();

    /* 게시판 목록(페이징 적용) */
    public List<BoardVO> getListPaging(Criteria cri);

    /* 게시판 조회 */
    public BoardVO getPage(int bno);

    /* 게시판 수정 */
    public int modify(BoardVO board);

    /* 게시판 삭제 */
    public int delete(int bno);

    /* 게시판 총 개수 
       기존의 getTotal() 메서드 쿼리에 추가적인 데이터가 필요 없었기 때문에 파라미터가 없었다.
       하지만 지금은 쿼리에서 데이터가 필요로 해졌기 때문에 'keyword' 데이터를 전달할 수 있도록 파라미터를 추가해야한다.
       BoardMapper.java BoardService.java BoardServiceImpl.java에 있는 getTotal() 메서드에 Criteria 클래스를 파라미터로 추가해준다. 
    */
    public int getTotal(Criteria cri);
}
