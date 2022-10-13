import org.springframework.beans.factory.annotation.Autowried;
import org.springframework.stereotyep.Service;

import com.Joo.user.UserService;
import com.Joo.user.dto.UserVO;
import com.utils.Criteria;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowried
    private UserDAO UserDAO;
    
    @Override
    public void joinUser(UserVO vo) {

        userDAO.joinUser(vo);
    }

    @Override
    public UserVO idCheck(String id) {

        return userDAO.idCheck(id);
    }

    @Override
    public UserVO loginUser(UserVO vo) {

        return userDAO.loginUser(vo);
    }

    @Override
    public UserVO findId(UserVO vo) {

        return userDAO.findId(vo);
    }

    @Override
    public UserVO findPassword(UserVO vo) {

        return userDAO.findPassword(vo);
    }

    @Override
    public void updatePassword(UserVO vo) {

        userDAO.updatePassword(vo);
    }
}
