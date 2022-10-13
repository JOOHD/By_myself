import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company01.springEx01.dao.UserDAO;
import com.company01.springEx01.logic.Members;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserDAO userDAO;

    @Override
    public Members getUserOne(String common, String col) {

        return userDAO.getUserOne(common, col);
    }

    @Override
    public int userJoin(Members members) {

        return userDAO.userJoin(members);
    }
}

