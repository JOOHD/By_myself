import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Respository;

import tody.common.common.CommandMap;
import tody.common.dao.AbstractDAO;


@Repository("boardDAO")
public class BoardDAO extends AbstractDAO {
    
    /*  
        AbstractDAO를 상속 받았기 때문에 selectList를 사용가능, selectList 메서드의 인자는 쿼리 이름, 
        쿼리가 실행되는데 필요한 변수 2가지 여기서는 
        "board.selectBoardList"가 쿼리 이름이고, commandMap이 쿼리 실행 시 필요한 변수들이다.
        쿼리 이름은 무엇에 따라 결정 되는 지는 SQL을 작성할 때 알 수 있다.      
    */

    // 게시판 목록
    // @SuppresWarnings : 컴파일 시 컴파일 경고를 사용하지 않도록 설정, 미확인 오퍼레이션 관련된 경고를 억제한다.
    @SuppresWarnings("unchecked")
    public List<Map<String, Object>> selectBoardList(Criteria cri) {
        return (List<Map<String, Object>>)selectList("board.selectBoardList", cri);
    }

    // 글 등록
    public void insertBoard(CommandMap commandMap) {
        insert("board.insertBoard", commandMap.getMap());
    }

    // 게시판 상세페이지
    @SuppressWarnings("unchecked")
    public Map<String, Object> detailBoard(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("board.detailBoard", map);
    }
    
    // 게시판 조회수 증가
    public void updateHitBoard(Map<String, Object> map) {
        update("board.updateHitBoard", map);
    }

    // 게시판 수정
    public void updateBoard(Map<String, Object> map) {
        update("board.updateBoard", map);
    }

    // 게시판 삭제
    public void deleteBoard(Map<String, Object> map) {
        update("board.deleteBoard", map);
    }

    // 총 게시글 개수
    public int countBoardList() {
        return (Integer) selectOne("board.countBoardList");
    }
}
