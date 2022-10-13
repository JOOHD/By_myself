import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vam.mapper.BoardMapper;
import com.vam.model.BoardVO;
import com.vam.model.Criteria;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper mapper;

    /* 게시판 등록 */
    @Override
    public void enroll(BoardVO board) {

        mapper.enroll(board);

    }

    /* 게시판 목록(페이징 적용) */
    @Override
    public List<BoardVO> getListPaging(Criteria cri) {
         
        return mapper.getListPaging(cri);

    }

    /* 게시판 목록 */
    @Override
    public List<BoardVO> getList() {

        return mapper.getList();

    }

    /* 게시판 조회 */
    @Override
    public BoardVO getPage(int bno) {

        // BoardMapper.java 인터페이스의 '게시판 조회' 메소드 호출
        return mapper.getPage(bno);

    }

    /* 게시판 수정 */
    @Override
    public int modify(BoardVO board) {

        // int를 반환해야하기 때문에 return 값에서 Mapper 메소드 호출
        return mapper.modify(board);

    }

    /* 게시판 삭제 */
    @Override
    public int delete(int bno) {

        return mapper.delete(bno);

    }

    /* 게시판 총 개수 */
    @Override
    public int getTotal(Criteria cri) {

         // int를 반환해야하기 때문에 return 값에서 Mapper 메소드 호출
        return mapper.getTotal(cri);
        
    }
}
