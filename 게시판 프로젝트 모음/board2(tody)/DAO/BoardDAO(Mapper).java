package board2.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Respository;

import tody.common.common.CommandMap;
import tody.common.dao.AbstractDAO;

/*
  AbstractDAO는 직접 만든 클래스를 상속받은 것이다. AbstractDAO를 상속받지 않아도 된다.
  상속 받지 않고 싶으면 AbstractDAO에 구현된 기능들을 바로 여기 DAO에 구현하면 된다. 
 */

@Repository("boardDAO")
public class BoardDAO extends AbstractDAO {
    
    /*  
        AbstractDAO를 상속 받았기 때문에 selectList를 사용가능, selectList 메서드의 인자는 쿼리 이름, 쿼리가 실행되는데 필요한 변수 2가지
        여기서는 "board.selectBoardList"가 쿼리 이름이고, commandMap이 쿼리 실행 시 필요한 변수들이다.
        쿼리 이름은 무엇에 따라 결정 되는 지는 SQL을 작성할 때 알 수 있다.      
    */

    /* 
    public List<Map<String, Object>> selectBoardList(CommandMap commandMap) { 
        return (List<Map<String, Object>>)selectList("board.selectBoardList", commandMap);
    }
    */

    // 게시판 목록
    // @SuppresWarnings : 컴파일 시 컴파일 경고를 사용하지 않도록 설정, 미확인 오퍼레이션 관련된 경고를 억제한다.
    @SuppressWarnings("unchecked")  
    public List<Map<String, Object>> selectBoardList(Criteria cri) { 
        return (List<Map<String, Object>>)selectList("board.selectBoardList", cri);
    }

    // 게시판 등록
    public void insertBaord(CommandMap commandMap) {
        
        /*
          query id(board.insertBoard)에 유의하자. sql의 id로도 쓰이기 때문에 오타가 있으면 안된다. 제발 여러번 확인하자
          board.insertBoard에서 board는 namespace이고, insertBoard는 id가 될 것이다.
         */
        insert("board.insertBoard", commandMap.getMap());
    }
    
    //  AbstractDAO라는 클래스를 상속받았기때문에 이렇게 썻다. AbstractDAO가 없다면, sqlSession을 이용해 작성하면 된다. 
    
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

    /*  
        삭제 컬럼으로 목록에서 안보이게 하기 때문에 update를 사용해서 삭제 컬럼을 update 시킨다.
        만약 데이터베이스에서 완전히 삭제를 하려고 한다면 delete를 사용하면 된다.
    */
    // 게시판 삭제
    public void deleteBoard(Map<String, Object> map) {
        update("board.deleteBoard", map);
    }

    // 총 게시글 개수
    public int countBoardList() {
        return (Integer) selectOne("board.countBoardList");
    }
}

/*
마이바티스는 Mapper 인터페이스를 제공한다.
우리는 DAO대신 매퍼를 사용, 매퍼를 사용하면 일일이 DAO를 만들지 않고 인터페이스만을 이용 좀더 편하게 개발 가능
마이바티스는 자바코드와 SQL문을 분리하여 편리하게 관리하도록 한다.
SQL문은 *xml 형식으로 저장한다.

package board.board.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import board.board.dto.BoardDto;

@Mapper
public interface BoardMapper {
    List<BoardDto> selectBoardList() throws Exception;
}

[BoardMapper.java]

<select id="selectBoardList" resultType="board.board.dto.BoardDto">

[sql.xml]

이렇게 SQL Mapper인 XML 파일의 namespace와 id를 맵핑하여 SQL문을 호출하여 결과값을 반환한다.

*/