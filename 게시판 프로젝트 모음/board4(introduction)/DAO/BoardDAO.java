import java.util.List;
import com.board.example.dto.BoardDTO;

public interface BoardDAO {

    // 게시물 목록 보기
    public List<BoardDTO> boardList() throws Exception;
    
    // 게시물 작성
    public void writerBoard(BoardDTO bdto) throws Exception;

    // 게시물 상세내용 불러오기
    public BoardDTO boardRead(int bno) throws Exception;

    // 게시물 수정
    public void boardUpdate(BoardDTO bdto) throws Exception;

    // 게시물 삭제
    public void boardDelete(int bno) throws Exception;
}
