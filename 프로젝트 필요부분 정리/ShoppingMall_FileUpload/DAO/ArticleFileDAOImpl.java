package DAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleFileDAOImpl {
    
    private static final String NAMESPACE = "com.cameldev.mypage.mappers.upload.AtricleFileMapper";

    @Inject
    private SqlSession SqlSession;

    // 게시글 첨부파일 추가
    @Override
    public void addAttach(String fullName, Integer article_no) throws Exception {

        /* 
            Map<String, String> map = new HashMap<>();
            이렇게 하는데 앞의 Map과 뒤의 HashMap의 글자가 다른 이유는 Map이 인터 페이스이기 때문이다.
            인터페이스는 선언만 가능하다. 객체 생성이 불가능한 것들이다.
            떄문에 자식인 HashMap으로 객체를 생성한다.
            HashMap은 본인의 메소드 외에 부모인 Map의 메소드들을 강제 상속받는다.

            List도 같은 맥락이다.
            List를 왜 ArrayList로 객체 생성하는지 몰랏느데 Map과 같은 이유이다.
            List는 인터페이스 부모니까, 바디 생성이 불가
            때문에 자식인 ArrayList, vector등으로 객체를 생성한다.
         */ 
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // HashMap<String, Object> map2 = new HashMap<>(); 이것도 가능
        paramMap.put("fullName", fullName);
        paramMap.put("article_no", article_no);
        sqlSession.insert(NAMESAPCE + ".addAttach", paramMap);
    }

    // 게시글 첨부파일 수정
    @Override
    public void replaceAttach(String fullName, Integer article_no) throws Exception {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("fullName", fullName);
        paramMap.put("article_no", article_no);
        sqlSession.insert(NAMESPACE + ".replaceAttach", paramMap);
    }

     // 게시글 첨부파일 삭제
     @Override
     public void deleteAttach(String fullName) throws Exception {
         sqlSession.delete(NAMESPACE + ".deleteAttach", fullName);
     }
 
     // 게시글 첨부파일 일괄 삭제
     @Override
     public void deleteAllAttach(Integer article_no) throws Exception {
         sqlSession.delete(NAMESPACE + ".deleteAllAttach", article_no);
     }
 
     // 특정 게시글의 첨부파일 갯수 갱신
     @Override
     public void updateAttachCnt(Integer article_no) throws Exception {
         sqlSession.update(NAMESPACE + ".updateAttachCnt", article_no);
     }
 출처: https://cameldev.tistory.com/68 [낙타의 개발일기 - CamelDev:티스토리]
}
