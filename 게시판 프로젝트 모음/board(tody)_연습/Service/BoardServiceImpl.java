import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4.Logger;
import org.springframework.stereotype.Service;

import tody.common.common.CommandMap;
import tody.prj.dao.BoardDAO;

public class BoardServiceImpl implements BoardService { // Service 인터페이스에서 정의된 메소드를 실제로 구현

    Logger log = Logger.getLogger(this.getClass());

    @Resource("boardVO")    // "boardDAO" 이름으로 bean을 수동으로 등록한다. Service에서 데이터 접근을 위해 DAO의 객체를 선언한다.
    private BoardDAO boardDAO;
    
    @Override
    // 게시판 목록
    // boardService 인터페이슨에서 정의된메소드를 실제로 구현할 수 있게
    public List<Map<String, Object>> selectBoardList(Criteria cri){ 
        // boardDAO클래스의 selectBoardList메서드를 호출하여 그 결과값을 return 한다.
        return boardDAO.selectBoardList(cri); 
    }

    @Override
    // 게시글 등록
    public void insertBaord(CommandMap commandMap) {
        boardDAO.insertBoard(commandMap);
    }

    @Override
    // 게시판 상세페이지
    public Map<String, Object> viewBoardDetail(Map<String, Object> map) {
        // 조회수 1증가시키는 동작
        boardDAO.updateHitBoard(map);
        // 게시글의 상세정보를 가져오는 쿼리를 호출한다.
        Map<String, Object> detail = boardDAO.detailBoard(map);
        return detail;
    }

    @Override
    // 게시글 수정때, 조회때 조회수를 증가시키면 안되기때문에 조회수 없는 쿼리를 호출
    public Map<Stirng, Object> selectBoardDetail(Map<String, Object> map) {
        return boardDAO.detailBoard(map);
    }

    @Override
    // 게시글 수정한 것을 업데이트 시키는 메소드
    public void updateBoard(Map<String, Object> map) {
        boardDAO.updateBoard(map);
    }

    @Override
    // 게시글 삭제
    public void deleteBoard(Map<String, Object> map) {
        boardDAO.deleteBoard(map);
    }

    @Override
    // 게시글 총 개수
    public int countBoardListTotal() {
        return boardDAO.countBoardList();
    }

