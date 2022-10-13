import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.portfolio.biz.employee.AdminService;
import com.portfolio.biz.employee.dto.AdminVO;
import com.portfolio.biz.order.OrderService;
import com.portfolio.biz.order.dto.OrderVO;
import com.portfolio.biz.product.ProductService;
import com.portfolio.biz.product.dto.ProductVO;
import com.portfolio.biz.qna.QnaService;
import com.portfolio.biz.qna.dto.QnaVO;
import com.portfolio.biz.utils.Criteria;
import com.portfolio.biz.utils.PageMaker;

@Controller
@SessionAttributes("adminUser")
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderServvice orderService;

    @Autowired
    private QnaService qnaService;

    @RequestMapping(value = "admin_login", method = RequestMethod.POST)
    public String adminLoginAction(AdminVO vo, Model model) {

        AdminVO adminUser = adminService.getAdmin(vo);

        if(employee == null) {
            
            return "admin/login";
        } else {

            int monthlyEarnings = orderService.monthlyEarnings();
            int annualEarnings = orderService.annualEarnings();
            int Tasks = orderService.beforeOrderHanling();
            int noReply = qnaService.berforQnaAnswer();

            model.addAttribute("noReply", noReply);
            model.addAttribute("Tasks", Tasks);
            model.addAttribute("monthlyEarnings", monthlyEarnings);
            model.addAttribute("annualEarnings", annualEarnings);

            return "admin/index";
        }
    }

}
