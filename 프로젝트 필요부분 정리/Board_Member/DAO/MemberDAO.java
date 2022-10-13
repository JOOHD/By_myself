package DAO;

import java.util.List;

import com.exampe.spring01.model.dto.MemberDTO;

public interface MemberDAO {
    
    // 회원 등록
    public List<MemberDTO> memberList();

    // 회원 가입
    public void insertMember(MemberDTO dto);

    // 회원 정보보기
    public MemberDTO viewMember(String userid);

    // 회원 삭제
    public void deleteMember(String userid);

    // 회원 정보수정
    public void updateMember(MemberDTO dto);

    // 로그인
    public boolean checkPw(String userid, String passwd);
}
