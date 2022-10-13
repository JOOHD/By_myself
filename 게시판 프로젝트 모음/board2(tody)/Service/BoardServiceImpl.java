package board2.Service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4.Logger;
import org.springframework.stereotype.Service;

import tody.common.common.CommandMap;
import tody.prj.dao.BoardDAO;

@Service("boardService")
public class BoardServiceImpl implements BoardService { // Service 인터페이스에서 정의된 메서드들을 실제로 구현한다.
    
    Logger log = Logger.getLogger(this.getClass());

    @Resource(name="boardDAO")  // "boardDAO" 이름으로 bean을 수동으로 등록한다. Service에서 데이터 접근을 위해 DAO의 객체를 선언한다.
    private BoardDAO boardDAO;

    @Override
    // 게시글 목록
//  public List<Map<String, Object>> selectBoardList(CommandMap commandMap) { 
    public List<Map<String, Object>> selectBoardList(Criteria cri) { // BoardService 인터페이스에서 정의된 메서드를 실제로 구현할 수 있게

        //  return boardDAO.selectBoardList(commandMap); 
        //  return boardMapper.selectBoardList(); Service가 매퍼를 호출하는 부분
        return boardDAO.selectBoardList(cri); // boardDAO클래스의 selectBoardList메서드를 호출하여 그 결과값을 return한다.
    }

    @Override
    // 게시판 등록
    //     void insertBoard(CommandMap commandMap);
    public void insertBoard(CommandMap commandMap) {
        boardDAO.insertBaord(commandMap);
    }

    /*
      ServiceImpl에서 2개의 DAO를 호출한다. 글의 상세페이지를 조회할 때, 우리는 두 가지를 수행해야 한다.
      첫번째로 글의 상세정보를 가져와야 하고, 두번째로 글의 조회수를 1 증가시켜야 한다. 이 두 가지의 동작을 
      하나의 트랜젝션에서 수행해야한다. ServiceIimpl은 하나의 페이지를 호출할 때 필요한 비즈니스 로직을 묶어서
      처리하는 것이다. 따라서 두 가지 동작을 serviceImpl에 처리하면 된다.

      물론 컨트롤러에서 상세정보를 불러오는 service와 조회수를 증가시키는 service를 따로 만들어서 호출해도 상관은 없다.
      이렇게 서비스를 따로 만든다면 '수정'을 할 때 서비스를 만들지 않고 상세정보를 불러오는 service를 그대로 사용하면 된다. 
     */

     @Override
     public Map<String, Object> viewBoardDetail(Map<String, Object> map) {
         // 조회수 1증가시키는 동작
         boardDAO.updateHitBoard(map);
         // 게시판의 상세정보를 가져오는 쿼리를 호출한다.
         Map<String, Object> detail = boardDAO.detailBoard(map);
         return detail;
     }

     @Override
     // 게시판 수정때 조회때 조회수를 증가시키면 안되기깨문에 조회수 없는 쿼리르 호출 
     public Map<String, Object> selectBoardDetail(Map<String, Object> map) {
         return boardDAO.detailBoard(map);
     }

     @Override
     // 게시판 수정한 것을 업데이트 시키는 메소드
     public void updateBoard(Map<String, Object> map) {
         boardDAO.updateBoard(map);
     }

     @Override
     // 게시판 삭제
     public void deleteBoard(Map<String, Object> map) {
         boardDAO.deleteBoard(map);
     }

     @Override
     // 게시글 총 개수
     public int countBoardListTotal() {
         return boardDAO.countBoardList();
     }
}
