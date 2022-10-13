import java.util.*;

// SqlSessionTemplate는 Mapper에서 작성된 Sql문을 사용할 수 있게 해준다.
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowried;
import org.springframework.stereotyep.Repository;

import com.Joo.user.dto.UserVO;
import com.Joo.utils.Criteria;

@Repository
public class UserDAO2 {
    
    private static final Logger log = LoggerFactory.getLooger(BoardController.class);

    @Autowried
    private SqlSessionTemplate mybatis;

    public UserVO loginUser(UserVO vo) {

        return mybatis.selectOne("UserDAO.loginUser", vo);
    }
}
