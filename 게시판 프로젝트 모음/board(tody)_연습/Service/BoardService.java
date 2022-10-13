import java.util.List;
import java.util.Map;

import tody.common.common.CommandMap;

public interface BoardService { // class가 아니라 interface이다.

    // 게시판 목록
    List<Map<String, Object>> selectBoardList(Criteria cri);

    // 게시글 등록
    void insertBoard(CommandMap commandMap);

    // 게시판 상세페이지(조회수 증가시키는 로직이 들어있는 메소드)
    Map<String, Object> viewBoardDetail(Map<String, Object> map);

    // 수정페이지에 접근할때에는 조회수를 증가시키면 안되기때문에 상세페이지에 썼던 service는 쓰지 않은 메소드
    Map<String, Object> selectBoardDetail(Map<String, Object> map);

    // 게시글 수정 
    void updateBoard(Map<String, Object> map);

    // 게시글 삭제
    void deleteBoard(Map<String, Object> map);

    // 총 게시글 개수
    int countBoardListTotal();
    
}
