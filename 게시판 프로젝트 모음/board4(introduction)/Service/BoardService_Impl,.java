import java.util.List;
 
import javax.inject.Inject;
 
import org.springframework.stereotype.Service;
 
import com.board.example.dao.BoardDAO;
import com.board.example.dto.BoardDTO;
 
@Service
public class BoardServiceImpl implements BoardService {
    @Inject
    BoardDAO boardDao;
 
    // 게시물 목록 불러오기
    @Override
    public List<BoardDTO> boardList() throws Exception {
        
        return boardDao.boardList(); 
    }

    // 게시물 글 쓰기
    @Override
    public void writerBoard(BoardDTO bdto) throws Exception {

        boardDao.writerBoard(bdto);
    }

    // 게시물 상세내용 불러오기
    @Override
    public BoardDTO boardRead(int bno) throws Exception {

        return boardDao.boardRead(bno);
    }

    // 게시물 수정
    @Override
    public void boardUpdate(BoardDTO bdto) throws Exception {

        boardDao.boardUpdate(bdto);
    }

    // 게시물 삭제
    @Override
    public void deleteBoard(int bno) throws Exception {
        
        boardDao.boardDelete(bno);
    }
}

