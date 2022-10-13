import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company01.springEx01.logic.Board;
import com.company01.springEx01.logic.Members;
import com.company01.springEx01.service.BoardService;
import com.company01.springEx01.service.UserService;

@Controller
@RequestMapping("jquery")
public class JqueryController {
    
    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    /* 
    boardWrite() 메서드를 보면 URL를 /jquery/boardWrite로 보내고 있다.
    Controller의 용도를 나눠서 가독성을 높일 것이다.
    
    ViewController : View를 보여주는(연결해주는) Controller
    JqueryController : jquery + ajax를 이용한 메서드들
    */

    /*
    function boardWrite(sub,con){
	
	$.ajax({
		
		url : "/jquery/boardwrite",
		type:'POST',
		data : {
			subject : sub,
			context : con
		},
		success:function(data){
			if(data == 1){
				alert("글 등록이 완료되었습니다.");
				location.href="/view/dashboard";
			}else {
				alert("글 등록 실패");
			}
		},error:function(){
			console.log("error");
		}
		
	})
	
    여기서 보면 boardwrite 메서드의 리턴 타입의 int라고 되어있는데,
    int로 스무스하게 return하면 "String or Object"로 리턴하라고 에러가 난다.

    원래 스프링은 Model 객체를 리턴해 "view"를 보여주려고 하는 성질이 존재한다.
    갑자기 int를 리턴하면 처음부터 형식에 맞지 않는 예외를 발생한다.

    그럼 아예 값을 리턴해주는 JSON 형태로 던져주면 된다.
}
     */

    // 게시판 글쓰기 JSON 형태 ResponseBody, ajax 형태로 받는다.
    @RequestMapping(value="jquery/boardwrite", method=RequestMethod.POST)
    @ResponseBody // Json으로 응답해주는 어노테이션
    public int boardwrite(Board board) {

        int result = 0;
        
        result = boardService.boardwrite(board);
        
        return result;
    }

    // 회원 로그아웃
    @RequestMapping("jquery/logout")
    public String logout(HttpSession session) {

        // 로그인된 유저 제거
        session.removeAttribute("loginUser");

        // 로그인 화면으로 이동
        return "/user/login";
    }

    // 회원 로그인
    @RequestMapping(value="jquery/login", method=RequestMethod.POST)
    @ResponseBody
    public int login(Members members, HttpSession session) {

        // result 초기값 = 0 설정
        int result = 0;

        // col 초기값 = null 설정
        String col = null;

        //col = "userId" 값 대입
        col = "userId";

        // 아이디가 존재하는지 입력값을 보내 DB에서 검사한다.
        Members userIdCheck = userService.getUserOne(members.getUserId(), col);

        // 아이디 유무 확인, 없을 경우
        if(userIdCheck == null) {

            // result = 2 반환
            result = 2;

        } else { // 아이디가 있을 경우

            // 아이디와 DB에 데이터 아이디와 같은지 확인
            if(members.getUserId().equals(userIdCheck.getUserId())) {

                // 비밀번호와 DB에 데이터 비밀번호와 같은지 확인
                if(members.getUserPassword().equals(userIdCheck.getUserId())) {
                
                    // "session"이라는 것에 로그인한 사용자의 정보를 담음
                    session.setAttribute("loginUser", userIdCheck);

                    // result = 3 반환 (3 == 비밀번호 DB 데이터와 일치)
                    result = 3;

                } else { // 비밀번호와 DB데이터가 다르면

                    // result = 2 반환 (2 == 비밀번호 DB 데이터와 불일치)
                    result = 2;
                }

            } else { // 아이디와 DB에 데이터 아이디와 일치하면

                // result = 2 반환 (2 == 아이디와 DB 데이터 아이디와 일치)
                result = 2;
            }
        }

        return result;
    }

    // 회원가입
    @RequestMapping(value="jquery/signUp", method=RequestMethod.POST)
    @ResponseBody
    public int signUp(Members members) {

        // result 초기값 = 0 설정
        int result = 0;

        // col 초기값 = null 설정
        String col = null;

        //col = "userId" 값 대입
        col = "userId";

        // 아이디가 존재하는지 입력값을 보내 DB에서 검사합니다.
        Members userIdCheck = userService.getUserOne(members.getUserId(), col);

        // 회원이 아닌 유저이면
        if(userIdCheck != null) {

            // 
            result = 2;
        }

         //col = "nickname" 값 대입
        col = "nickname";

        // 닉네임이 존재하는지 입력값을 보내 DB에서 검사합니다.
        Members userNicknameCheck = userService.getUserOne(members.getNickname(), col);

        // 닉네임이 맞지 않으면
        if(userNicknameCheck != null) {

            // result = 3 반환 (이미 존재하는 닉네임 입니다.)
            result = 3;
        }


        if(result < 2) {

            // result = 0, 1이면, (회원)
            result = userService.userJoin(members);
        }

        return result;
    }
}
