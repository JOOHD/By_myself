package DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.spring01.model.dto.MemberDTO;

// 현재 클래스를 스프링에서 관리하는 dao bean으로 설정
@Repository
public class MemberDAOImpl implements MemberDAO{
    
    // mybatis의 sqlSession 객체를 스프링에서 주입
    // 의존관계 주입 느슨한 결합, 제어의 역전
    // @Inject 어노테이션이 있어 sqlSession은 Null 상태가 아닌 외부에서 객체를 주입받는 형태가 된다.
    @Inject
    SqlSession sqlSession;

    // 회원목록
    @Override
    public list<MemberDTO> memberList() {

        return sqlSession.selectList("member.memberList");
    }

    // 회원가입
    @Override
    public void insertMember(MemnberDTO dto) {

        //auto commit, auto close     
        sqlSession.insert("member.insertMember", dto);
    }

    // 회원 정보 상세 보기
    @Override
    public MemberDTO viewMember(String userid) {

        // 레코드 1개 : selectOne(), 2개 이상 : selectList()
        return sqlSession.selectOne("member.viewMember", userid);
    }

    // 회원 삭제 하기
    @Override
    public void deleteMember(String userid) {

        sqlSession.delete("member.deleteMember", userid);
    }

    // 회원 정보 수정
    @Override
    public void updateMember(MemberDTO dto) {

        sqlSession.update("member.updateMember", dto); 
    }

    // 로그인 체크
    @Override
    public boolean checkPw(String userid, String passwd) {

        boolean result = false;

        // mapper에 2개 이상의 자료를 전달할 때 : map, dto 사용
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("password", passwd);
        
        int count = sqlSession.selectOne("member.checkPw", map);

        // 비번이 맞으면 1=>true, 틀리면 0 => false 리턴
        if(count == 1) result = true;
        
        return result;
    }
}
