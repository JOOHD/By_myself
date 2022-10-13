package board2.Service;

import java.util.List;
import java.util.Map;

import tody.common.common.CommandMap;

public interface BoardService { // class가 아니라 interface이다.

//  List<Map<String, Object>> list = boardService.selectBoardList(commandMap); 
//  List<Map<String, Object>> selectBoardList(CommandMap commandMap);
    // 게시판 목록
    List<Map<String, Object>> selectBoardList(Criteria cri);

    // 글 등록
    void insertBoard(CommandMap commandMap);

    // 게시판 상세페이지(조회수 증가시키는 로직이 들어있는 메소드)
    Map<String, Object> viewBoardDetail(Map<String, Object> map);

    /*
      수정 페이지는 상세 페이지철머 글 정보를 전부 가져온다. 그렇다면 상세페이지에 썼던 service를 그대로 써도
      되지 않을까? 상관없다. 같은 기능을 한다면 service를 그대로 가져와도 전혀 상관없다. 하지만 나는 상세페이지의
      service는 조회수를 증가시켜주는 로직이 들어가있다. 수정페이지에 접근할때에는 조회수를 증가시키면 안되기때문에
      상세페이지에 썼던 service는 쓰지 않을 것이다. 
     */
    // 수정페이지에 접근할때에는 조회수를 증가시키면 안되기때문에 상세페이지에 썼던 service는 쓰지 않은 메소드
    Map<String, Object> selectBoardDetail(Map<String, Object> map);

    // 글 수정 메소드
    void updateBoard(Map<String, Object> map);

    /*
      글 삭제에는 두 가지 방법이 있따. 첫번째는 데이터베이스에서 삭제하는 방법과 두 번째는 삭제 컬럼으로
      안보이게 하는 방법. 둘 중 원하는 방법을 사용하면 된다. 둘 다 각자 장단점이 있다. 나는 삭제 컬럼을 만들어
      목록에서 보이지 않게 할 것이다. 즉, 목록에는 보이지 않지만 데이터베이스에는 존재한다. 
     */
    // 글 삭제
    void deleteBoard(Map<String, Object> map);

    // 총 게시글 개수
    int countBoardListTotal();
}
