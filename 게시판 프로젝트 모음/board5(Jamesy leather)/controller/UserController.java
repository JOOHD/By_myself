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
public class UserController {
    
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
    public String loginAction(UserVO vo, Model model) {

        // userService의 loginUser메소드 호출(해당 사용자의 정보를 가져온다.)
        UserVO loginUser = userService.loginUser(vo);

        if(loginUser == null) { // 로그인 실패

            model.addAttribute("check", 1);
            model.addAttribute("message", "아이디와 비밀번호를 확인해주세요.");

            // 로그인 실패 시, 로그인 창으로 이동
            return "member/login";

        } else { // 로그인 성공 

            model.addAttribute("loginUser", loginUser);

            // 로그인 성공 후, 홈페이지로 이동
            return "redirect:index";
        }
    }

    // 아이디 찾기 페이지 이동
    @RequesetMapping(value = "find_id_form")
    public String findIdView() {

        return "member/findId";
    }

    // 아이디 찾기 실행
    @RequestMapping(value="find_id", method=RequestMethod.POST)
    public String findIdAction(UserVO vo, Model model) {

        UserVO user = userService.findId(vo);

        if(user == null) { // userId가 없을 경우

            model.addAttribute("check", 1);

        } else {           // userId가 있을 경우

            model.addAttribute("check", 2);

                           // 기존 userId를 가져온다.
            model.addAttribute("id", user.getId());
        }
    }

    // 비밀번호 찾기 페이지로 이동
    @RequestMapping(value = "find_password_form")
    public String findPasswordView() {

        return "member/findPassword";
    }

    // 비밀번호 찾기 실행
    @RequestMapping(value="find_password", method=RequestMethod.POST)
    public String findPasswordAction(UserVO vo, Model model) {

        // UserVO 타입, user 변수 = userService.findPassword(UserVO객체인 변수 vo 설정) 호출
        UserVO user = userService.findPassword(vo);

        if(user == null) { // user가 없을 경우

            model.addAttribute("check", 1);

        } else { // user가 있을 경우

            model.addAttribute("check", 0);

            // user.getId() id 불러오기
            model.addAttribute("update", user.getId()); 
        }
        return "member/findPassword";
    }

    // 비밀번호 바꾸기 실행
    @RequestMapping(value="update_password", method=RequestMethod.POST) 
    String updatePasswordActioin(@RequestParam(value="updateId", defaultValue="", required = false)
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
    @RequestMapping(value="check_password_view")
    public String checkPasswordForModify(HttpSession session, Model model) {

        /*
           - "Key"로 바인딩된 객체를 돌려주고, "Key"로 바인딩된 객체가 없다면 null를 돌려준다.
           - Value는 세션을 저장할 때 Object형으로 업캐스팅을 했으므로, 가져올 땐 원래대로 다운캐스팅 해야 한다.
         */

        // "USER"로 바인딩된 객체를 돌려준다. ("USER"로 바인딩된 객체가 없다면 null)
        // session에 저장된 회원 정보 호출
        UserVO loginUser = (UserVO)session.getAttribute("loginUser");

        if(loginUser == null) { // 유저 존재 안할 시

            // 로그인 창 페이지로 다시 이동
            return "memebr/login";
        } else {                // 유저 존재 할 경우

            // 내정보 페이지에 있는 비밀번호 수정 페이지로 이동
            return "mypage/checkformodify";
        }
    }

    // idCheck 란
    /*
        -RequestParam(String) : url파라미터로 전달받은 value를 메서드의 파라미터로 받을 수 있게 해주는 어노테
                            Ex) String id = request.getParameter("id");
        -defaultValue(String) : 해당 파라미터의 기본 값
        -required(boolean)    : 해당 파리미터가 반드시 필수인지 여부, 기본값은 true, 
                                 파라미터의 value로 null이 들어오는 것을 방지하기 위해서 required 설정을 false로 한다.(default:true)
                                 required = true가 기본 값이 되면 null 값이 들어올경우 error가 발생한다.   
        
        -@PathVariable : 변수명을 바꿔서 사용하고 싶은 경우 @PathVariable("템플릿 변수명") url로 들어온 템플릿 변수명과 맵핑을 시켜주면 된다.

        예를 들어 /user?name=hellozin 이라는 요청에서 "hellozin" 이라는 값을 가져오기 위해 아래와 같이 컨트롤러를 구현하면

        @GetMapping("/user")
        @ResponseBody
        public String getUserName(@RequestParam String name) {
            
            return name;
        }
        
        @RequestParam을 통해 name 변수에 "hellozin"이라는 문자열을 받아 처리할 수 있습니다.
        하지만 저대로 사용하게 되면 요청 쿼리 스트링에 "name" 필드가 없을 경우 즉, /user 와 같이 @RequestParam이 적용된 필드가 없으면 Bad Request, Required String parameter 'name' is not present 라는 예외를 발생시킵니다.
        이를 해결하기 위해 @RequestParam(required = false) 와 같이 required 속성을 추가하면 해당 필드가 쿼리스트링에 존재하지 않아도 예외가 발생하지 않습니다.
     */
    @RequestMapping(value = "/id_check_form")
    public String idCheckFormView(@RequestParam(value = "id", defaultValue = "", required = false)
            String id, Model model) {

            model.addAttribute("id", id);

            return "member/idcheck";
    }

    /*
      페이지 넘어갈 때는 GET방식이기 떄문에 method를 설정핼줄 필요가 없지만
      POST 방식을 이용할 때는 method에 POST방식이라는 것(method=RequestMethod.POST)를 Mapping 안에 작성
      그리고 뷰(View-JSP) 부분에서 데이터를 받아올 때는 @RequestParam을 이용하여 input 부분 name을 통해 데이터를 받아올 수 있따.
     */
    // idCheck 버튼 클릭시 action
    @RequestMapping(value = "/id_check_form", method = RequestMethod.POST)
    public String idCheckAction(HttpServletRequest request, Model model, UserVO  vo) {

        /*
            위에서 설명했듯이 서블릿은 HTTP 요청 메시지를 생성해주는데 그 결과를 HttpServletRequest 객체에 담아서 제공한다.
            서버는 이 객체를 통해 여러 유용한 기능들을 편리하게 사용할 수 있다.

            임시 저장소 기능
            해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능을 제공한다.
            저장: request.setAttribute(name, value)
            조회: request.getAttribute(name)
         */
        String id = request.getParameter("id");
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

    // 회원가입 페이지로 이동
    @RequestMapping(value = "/join_form")
    public String joinFormView() {

        return "member/join";
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
