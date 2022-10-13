import java.util.List;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.example.dto.BoardDTO;

@Repository
public class BoardDAO_Impl implements BoardDAO {
    
    @Inject
    SqlSession sqlSession;

    // 게시물 목록 불러오기
    @Override
    public List<BoardDTO> boardList() throws Exception {

        // DB에서 레코드의 단일 행을 가져올 것이므로 selectOne을 사용한다.
        return sqlSession.selectList("board.boardList");
    }

    // 게시물 글쓰기
    @Override
    public void writerBoard(BoardDTO bdto) throws Exception {

        sqlSession.insert("board.boardWriter", bdto);
    }

    // 게시물 상세내용 불러오기
    public BoardDTO boardRead(int bno) throws Exception {

        // DB에서 레코드의 단일 행을 가져올 것이므로 selectOne을 사용한다.
        return sqlSession.selectOne("board.boardRead", bno);
    }

    // 게시물 수정
    @Override
    public void boardUpdate(BoardDTO bdto) throws Exception {

        sqlSession.update("board.boardUpdate", bdto);
    }

    // 게시물 삭제
    @Override
    public void boardDelete(int bno) throws Exception {
        
        sqlSession.delete("board.boardDelete", bno);
    }
}
