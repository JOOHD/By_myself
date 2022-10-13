import javax.servlet.http.HttpServletReqeust;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowried;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;

import com.Joo.employee.dto.AdminVO;
import com.Joo.user.UserService;
import com.Joo.user.dto.UservVO;
import com.Joo.utils.Criteria;
import com.Joo.utils.PageMaker;

@Controller
@SessionAttribute("loginUser")
public class UserController4 {
    
    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    /* 로그인 페이지로 이동 */
    @RequestMapping(value="/login_form")
    public String loginFormView() {

        return "member/login";
    }

    /* 로그인 실행 */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginAction(UserVO vo, Model model) {

        UserVO loginUser = userService.loginUser(vo);

        if(loginUser == null) {

            model.addAttribute("check", 1);
            model.addAttribute("message", "아이디와 비밀번호를 확인해주세요");

            return "member/login";
        } else {

            model.addAttribute("loginUser", loginUser);

            return "redirect:index";
        }
    }

    /* 아이디 찾기 실행 */
    @RequestMapping(value="find_id", method=RequestMethod.POST)
    public String findIdAction(UserVO vo, Model model) {

        UserVO user = userService.findId(vo);

        if(user == null) {

            model.addAttribute("check", 1);
            
        } else {

            model.addAttribute("check", 2);

            model.addAttribute("id", user.getId());
        }
    }

    /* 비밀번호 찾기 실행 */
    @RequestMapping(value="find_password", method=RequstMethod.POST)
    public String findPasswordAction(UserVO vo, Model model) {

        UserVO user = userService.findPassword(vo);

        if(user == null) {

            model.addAttribute("check", 1);

        } else { 

            model.addAttribute("check", 0);

            // user.getId() id 불러오기
            model.addAttribute("update", user.getId()); 
        }
        return "member/findPassword";
    }

    /* 비밀번호 바꾸기 실행 */
    @RequestMapping(value="update_password", method=RequestMethod.POST)
    String updatePasswordAction(@RequestParam(value="updateId", defaultValue="", required=false)
                                    String id, UserVO vo) {
            vo.setId(id);

            log.info(vo);

            userService.updatePassword(vo);

            return "member/findPasswordConfirm";
    }

    /* 비밀번호 바꾸기할 경우 성공 페이지 이동 */
    @RequestMapping(value="check_password_view")
    public String checkPasswordForModify(HttpSession session, Model model) {

        UserVO loginUser = (UserVO)session.getAttribute("loginUser");

        if(loginUser == null) {

            return "member/login";
        } else {

            return "mypage/checkforModify";
        }
    }

    /* 아이디 체크 */
    @RequestMapping(value="/id_check_form")
    public String idCheckFormView(@RequestParam(value="id", defaultValue="", required=false)
            String id, Model model) {

        model.addAttribute("id", id);

        return "member/idcheck";
    }

    /* 아이디 체크 실행 */
    @RequestMapping(value="/id_check_form", method=RequestMethod.POST)
    public String idCheckAction(HttpServletRequest request, Model model, UserVO vo) {

        String id = request.getparameter("id");
        UserVO vo = userService.idCheck(id);

        if(vo == null) {
            //  아이디가 존재하지 않을 때
                model.addAttribute("check", 0);
        } else {
            //  아이디가 존재할 때
                model.addAttribute("check", 1);
        }
            //  존재하는 아이디 보여줌
                model.addAttribute("id", id);

            //  idcheck return    
                return "member/idcheck";
    }

    // 회원가입 실행
    @RequestMapping(value="/join", method = RequestMethod.POST)
    public String joinAction(@RequestParam(value = "addr1", defaultValue = "", required = false) String addr1,
                                @RequstParam(value = "addr2", defaultValue = "", required = false) String addr2, UserVO vo) {

        // 주소 기입
        vo.setAddress(addr1 + " " + addr2);

        // 이메일 기입
        vo.setEmail(vo.getId() + "@email.com");

        // 회원정보 불러오기
        log.info(vo);

        // userService.joinUser 메소드 호출, (vo) 유저 불러오기
        userService.joinUser(vo);

        return "member/login";
    }

    // 로그아웃 실행
    @RequestMapping(value="/logout")
    public String logoutAction(SessionStatus status) {

        /*
          SessionStatus는 @SessionAttributes를 활용해 
          Session에 남긴 데이터를 정리하는데 활용을 하는 인터페이스 입니다.
         */

        status.setComplete();

        return "redirect:index";
    }
}
