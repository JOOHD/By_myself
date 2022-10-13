package Service;

import java.util.List;
 
import javax.inject.Inject;
 
import org.springframework.stereotype.Service;
 
import com.example.spring01.model.dao.MemberDAO;
import com.example.spring01.model.dto.MemberDTO;
 
//현재 클래스를 스프링에서 관리하는 service bean으로 설정
@Service
public class MemberServiceImpl implements MemberService {
    //dao 인스턴스를 주입시킴
    @Inject
    MemberDAO memberDao;
    
    // 회원 목
    @Override
    public List<MemberDTO> memberList() {

        return memberDao.memberList();
    }
    // 회원 가입
    @Override
    public void insertMember(MemberDTO dto) {

        memberDao.insertMember(dto);
    }
    // 회원 상세보기
    @Override
    public MemberDTO viewMember(String userid) {

        return memberDao.viewMember(userid);
    }
    // 회원 삭제
    @Override
    public void deleteMember(String userid) {

        memberDao.deleteMember(userid); 
    }
    // 회원 정보 수정
    @Override
    public void updateMember(MemberDTO dto) {

        memberDao.updateMember(dto); 
    }
    // 로그인
    @Override
    public boolean checkPw(String userid, String passwd) {

        return memberDao.checkPw(userid, passwd); 
    }
}
