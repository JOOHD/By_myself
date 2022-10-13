import java.util.List;
 
import javax.inject.Inject;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
import com.example.spring01.model.dto.MemberDTO;
import com.example.spring01.service.MemberService;

@Controller
public class MemberController_Copy {
    
    // MemberService 인스턴스를 주입시킴
    @Inject
    MemberService memberService;

    // 회원 등록
    @RequestMapping("member/list.do")
    public String memberList(Model model) {

        List<MemberDTO> list = memberService.memberList();

        model.addAttribute("list", list);

        // WEV-INF/views/member/member_list.jsp
        return "member/member_list";
    }

    // 글 쓰기
    @RequestMapping("member/write.do")
    public String write() {

        return "member/write";
    }

    // 회원 가입
    // @ModelAttribute : 폼에서 전달된 값을 저장하는 객체
    @RequestMapping("member/insert.do")
    public String insert(@ModelAttribute MemberDTO dto) {

        // System.out.println(dto);
        memberService.insertMember(dto);

        return "redirect:/member/list.do";
    }

    // 회원정보 보기
    // @RequestParam : request.getParameter("변수명") 대체
    @RequestMapping("member/view.do")
    public String view(@RequestParam String userid, Model model) {

        // 모델에 자료 저장
        model.addAttribute("dto", memberService.viewMember(userid));

        // view.jsp 이동
        return "member/view";
    }

    // 회원정보 수정
    // java.util.Date
    @RequestMapping("member/update.do")
    public String update(MemberDTO dto, Model model) {

        // 비밀번호 체크
        boolean result = memberService.checkPw(dto.getUserid(), dto.getPasswd());

        if(result) {

            // 회원정보 수정
            memberService.updateMember(dto);

            // 수정 후 목록으로 이동
            return "redirect:/member/list.do"; //redirect

        } else {
            model.addAttribute("dto", dto);
            model.addAttribute("join_date", memberService.viewMember(dto.getUserid()).getJoin_date());
            model.addAttribute("message", "비밀번호를 확인하세요.");

            return "membner/view"; 
        }

        @RequestMapping("member/delete.do") 
        public String delete(String userid, String passwd, Model model) {

            // boolean 으로 참, 거짓 = 서비스(비밀번호 확인) 메서드 (필요한 인자 값 param1, param2)
            boolean result = memberService.checkPw(userid, passwd);

            if(result) {

                // service 클래스의 deleteMember 메소드 호출
                memberServcie.deleteMember(userid);

                // list로 이동
                return "redirec:/member/list.do";
                
            } else {

                model.addAttribute("message", "비밀번호를 확인하세요");
                model.addAttribute("dto", memberService.viewMember(userid));

                // view로 이동
                return "member/view";
            }
        }
    }
}
