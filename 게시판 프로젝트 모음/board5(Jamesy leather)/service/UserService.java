import java.util.List;

import com.Joo.user.dto.UserVO;
import com.Joo.utils.Criteria;

public interface UserService {
    
    UserVO loignUser(UserVO vo);

    UserVO findId(UserVO vo);

    UserVO findPassword(UserVO vo);

    void updatePassword(UserVO vo);

    void joinUser(UserVO vo);

    UserVO idCheck(String id);
    
}
