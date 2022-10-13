import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("user/*")
public class UserController {
    
    // 로그인
    @RequestMapping(value="user/login", method=RequestMethod.GET)
    public ModelAndView login() {

        ModelAndView mav = new ModelAndView();

        return mav;
    }

    // 회원가입
    @RequestMapping(value="user/signUp", method=RequestMethod.GET)
    public ModelAndView signUp() {

        ModelAndView mav = ModelAndView();

        return mav;
    }
}
