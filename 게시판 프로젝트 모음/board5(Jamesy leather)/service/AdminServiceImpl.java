import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Joo.employee.AdminService;
import com.Joo.employee.dto.AdminVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminDAO adminDAO;

	@Override
	public AdminVO getAdmin(AdminVO vo) {
		
		return adminDAO.getAdmin(vo);
	}

}
