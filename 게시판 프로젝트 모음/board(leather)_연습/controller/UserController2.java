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

public class UserController2 {
    
    @Autowried
    private UserService userService;
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // about 페이지로 이동
    @RequestMapping(value = "/about")
    public String aboutPageView() {

        return "about";
    }

    // 로그인 페이지로 이동
    @RequestMapping(value = "/login_form")
    public String loginFormView() {

        return "member/login";
    }

    // 로그인 실행
    @RequestMapping(value = "/login", method = RequestMethod.POST) 
    public UserVO loginAction(UserVO vo, Model model) {

        // userService의 loginUser메소드 호출(해당 사용자의 정보를 가져온다.)
        UserVO loginUser = userService.loginUser(vo);

        if(loginUser == null) { // 로그인 실패

            model.addAttribute("check", 1);
            model.addAttribute("message", "아이디와 비밀번호를 확인해주세요.")

            return "member/login";
        } else {    // 로그인 성공

            model.addAttribute("loginUser", loginUser);

            // 로그인 성공 후, 홈페이지로 이동
            return "redirect:index";
        }
    }

    // 아이디 찾기 페이지 이동
    @RequestMapping(value = "find_id_form")
    public String findIdView() {

        return "member/findId";
    }

    // 아이디 찾기 실행
    @RequestMapping(value = "find_id", method = ReqeustMethod.POST)
    public String findIdAction(UserVO vo, Model model) {

        // userService.findId() 메소드 호출(user의 정보를 가져온다.)
        UserVO user = userService.findId(vo);

        if(user == null) { // 아이디가 없을 경우

            model.addAttribute("check", 1);

        } else {           // 아이디가 있을 경우

            model.addAttribute("check", 2);

                           // 기존 아이디를 가져온다.
            model.addAttribute("id", id);
        }
    }

    // 비밀번호 찾기 페이지로 이동
    @RequestMapping(value = "find_password_form")
    public String findPasswordView() {

        return "/member/findPassword";
    }

    // 비밀번호 찾기 실행
    @RequestMapping(value = "find_password", method = RequestMethod.POST)
    public String findPasswordAction(UserVO vo, Model model) {

        // UserVO 타입, user 변수 = userService.findPassword(UserVO 객체인 변수 VO 설정) 호출
        UserVO user = userService.findPassword(vo);
        
        if(user == null) {  // user가 없을 경우

            model.addAttribute("check", 1);

        } else {            // user가 있을 경우

            model.addAttribute("check", 0);

            // user.getId() id 불러오기
            model.addAttribute("update", user.getId());
        }
        return "member/findPassword";
    }

    // 비밀번호 바꾸기 실행
    @RequestMapping(value="update_password", method = RequestMethod.POST)
    String updatePasswordAction(@RequestParam(value="updateId", defaultValue="", required = false)
                                    String id, UserVO vo) {
            // 아이디 입력(id)
            vo.setId(id);

            // 유저 정보
            log.info(vo);

            // 서비스 클래스의 updatePassword() 메소드 호출, vo 입력
            userService.updatePassword(vo);

            return "member/findPasswordConfirm";
    }

    // 비밀번호 바꾸기할 경우 성공 페이지 이동
    @RequestMapping(value = "check_password_view")
    public String checkPasswordForModify(HttpSession session, Model model) {

        // "USER"로 바인딩된 객체를 돌려준다. ("USER"로 바인됭된 객체가 없다면 NULL)
        UserVO loginUser = (UserVO)session.getAttribute("loginUser");

        if(loginUser == null) { // 로그인유저 존재 안할 시

            // 로그인 창 페이지로 다시 이동
            return "member/login";
        } else {                // 유저 존재 할 경우

            // 내정보 페이지에 있는 비밀번호 수정 페이지로 이동
            return "mypage/checkformodify";
        }
    }

    // idCheck 버튼 클릭시 action
    @RequestMapping(value = "/id_Check_form", method = RequestMethod.POST)
    public String idCheckAction(HttpServletRequest request, Model model, UserVO vo) {

        // HttpServletRequest의 getParameter로 id 조회
        String id = request.getParameter("id");

        // userService의 idCheck메소드 호출, (id)정보를 가져온다
        UserVO vo = userService.idCheck(id);

        if(vo == null) {

            // 아이디가 존재하지 않을 떄
            model.addAttribute("check", 0);
        } else {

            // 아이디가 존재할 때
            model.addAttribute("check", 1);
        }

            // 존재하는 아이디 보여줌
            model.addAttribute("id", id);

            return "member/idcheck";
    }

    // 회원가입 페이지로 이동
    @RequestMapping(value = "/join_form")
    public String joiinFormView() {

        return "member/join";
    }

    // 회원가입 실행
    @RequesetMapping(value = "/join", method = RequestMethod.POST)
    public String joinAction(@RequestParam(value = "adddr1", defaultValue = "", required = false) String addr1,
                                @RequstParam(value = "addr2", defaultValue = "", required = false) String addr2, UserVO vo) {

        vo.setAddress(addr1 + " " + addr2);
        vo.setEmail(vo.getId() + "@gmail.com");
        log.info(vo);
        userService.joinUser(vo);

        return "member/login";
    }

    // 로그아웃 실행
    @RequestMapping(value="/logout")
    public String logoutAction(SessionStatus status) {

        status.setComplete();

        return "redirect:index";
    }
}