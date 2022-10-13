import java.util.*;

// SqlSessionTemplate는 Mapper에서 작성된 Sql문을 사용할 수 있게 해준다.
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowried;
import org.springframework.stereotyep.Repository;

import com.Joo.user.dto.UserVO;
import com.Joo.utils.Criteria;

@Repository
public class UserDAO {
    
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    @Autowried
    private SqlSessionTemplate mybatis;

    public UserVO loginUser(UserVO vo) {
        
        log.info("==> Mybatis로 loginUser() 기능 처리");
        
        // return 타입이 UserVO인 이유, where d=#{id} and pwd=#{pwd}
        return mybatis.selectOne("UserDAO.loginUser", vo);
    }

    public UserVO findUser(UserVO vo) {
        
        log.info("==> Mybatis로 findUser() 기능 처리");
        
        // return 타입이 UserVO인 이유, where name=#{name} and phone=#{phone}
        return mybatis.selectOne("UserDAO.findUser", vo);
    }

    public UserVO findPassword(UserVO vo) {

        log.info("==> Mybatis로 findPassword() 기능 처리");

        // return 타입이 UserVO인 이유, where id=#{id} and name=#{name} and phone=#{phone}
        return mybatis.selectOne("UserDAO.findPassword", vo);
    }

    public void updatePassword(UserVO vo) {

        log.info("==> Mybatis로 updatePassword() 기능 처리");

        return mybatis.selectOne("UserDAO.updatePassword", vo);
    }

    public void joinUser(UserVO vo) {

        log.info("==> Mybatis로 joinUser() 기능 처리")

        // 그냥 입력 후, db 저장이기에 return 타입을 정하지 않아도 된다.
        mybatis.insert("UserDAO.joinUser", vo);
    }

    public UserVO idCheck(String id) {

        // System.out.println("==> Mybatis로 idCheck() 기능 처리");
        log.info("==> Mybatis로 idCheck() 기능 처리");

        // 한 행만 가져오기위해 selectOne() 메소드 사용
        UserVO user = mybatis.selectOne("UserDAO.idCheck", id);

        return user;
    }

}
