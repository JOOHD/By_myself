import java.uitl.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowried;
import org.springframework.stereotyep.Repository;

import com.Joo.user.dto.UserVO;
import com.Joo.utils.Criteria;

@Repository
public class UserDAO {
    
    private static final Logger log = Logger.factory.getLogger(BoardController.class);

    @Autowried
    private SqlSessionTemplate mybatis;

    // 로그인
    public UserVO loginUser(UserVO vo) {

        log.info("==> Mybatis로 loginUser() 기능 처리");

        return mybatis.selectOne("UserDAO.loginUser", vo);
    }

    // 회원 찾기
    public UserVO findUser(UserVO vo) {

        log.info("==> Mybatis로 findUser() 기능 처리");

        return mybatis.selectOne("UserDAO.findUser", vo);
    }

    // 비밀번호 찾기
    public UserVO findPassword(UserVO vo) {

        log.info("==> Mybatis로 findPassword() 기능 처리");

        return mybatis.selectOne("UserDAO.updatePassword", vo);
    }

    // 회원가입
    public void joinUser(UserVO vo) {

        log.info("==> Mybatis로 joinUser() 기능 처리");

        mybatis.insert("UserDAO.joinUser", vo);
    }

    // 아이디 확인
    public UserVO idCheck(String id) {

        log.info("==> Mybatis로 idCheck() 기능 처리");

        UserVO user = mybatis.selectOne("UserDAO.idCheck", id);

        return user;
    }
}
