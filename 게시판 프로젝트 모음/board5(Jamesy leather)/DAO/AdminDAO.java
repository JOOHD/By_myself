import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotaion.Autowired;
import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;

import com.Joo.employee.dto.AdminVO;

@Repository
public class AdminDAO {
    
    @Autowired
    // 데이터를 가져오는 SQL을 이용하여 데이터를 가져온다.
    private SqlSessionTemplate mybatis;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public AdminVO getAdmin(AdminVO vo) {

            log.info("==> Mybatis로 getAdmin() 기능 처리");
            
            AdminVO admin = mybatis.selectOne("AdminDAO.getAdmin", vo);

            log.info("adminDAO admin : " + admin);

            return admin;
    }
}
