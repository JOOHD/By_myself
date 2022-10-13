import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vam.mapper.BoardMapper;
import com.vam.model.BoardVO;
import com.vam.model.Criteria;

@Service
// ServiceImpl은 service 클래스의 메소드를 구현하는 클래스이다.
public class BoardServiceImpl implements BoardService {
    
    @Autowired 
    private BoardMapper mapper; // BoarMapper 인터페이스 주입

    /* 게시판 등록 */
    @Override
    public void enroll(BoardVO board) {

        mapper.enroll(board);       
    }

    /* 게시판 목록(페이징 적용) */
    @Override
    public List<BoardVO> getListPaging(Criteria cri) {

        // return 값은 메소드의 결과 값을 돌려주는 명령어다.
        return mapper.getListPaging(cri);
    }

    /* 게시판 목록 */
    @Override
    public List<BoardVO> getList() {

        return mapper.getList();
    }

    /* 게시판 조회 */
    @Override
    public BoardVO getPage(int bno) { // 게시판 '조회' 메소드 호출 

        // BoardMapper.java 인터페이스의 '게시판 조회' 메소드 호출
        return mapper.getPage(bno);
    }

    /* 게시판 수정 */
    @Override
    public int modify(BoardVO board) { // 선언한 메소드 구현, 구현 내용은 Mapper 메소드 호출

         // int를 반환해야하기 때문에 return 값에서 Mapper 메소드 호출
        return mapper.modify(board);
    }

    /* 게시판 삭제 */
    @Override
    public int delete(int bno) {

        // int를 반환해야하기 때문에 return 값에서 Mapper 메소드 호출
        return mapper.delete(bno);
    }

    /* 게시판 총 개수 
       기존의 getTotal() 메서드 쿼리에 추가적인 데이터가 필요 없었기 때문에 파라미터가 없었다.
       하지만 지금은 쿼리에서 데이터가 필요로 해졌기 때문에 'keyword' 데이터를 전달할 수 있도록 파라미터를 추가해야한다.
       BoardMapper.java BoardService.java BoardServiceImpl.java에 있는 getTotal() 메서드에 Criteria 클래스를 파라미터로 추가해준다.  
    */
    @Override
    public int getTotal(Criteria cri) {

         // int를 반환해야하기 때문에 return 값에서 Mapper 메소드 호출
        return mapper.getTotal(cri);
    }
}