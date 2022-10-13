import java.util.List;
 
import javax.inject.Inject;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
import com.example.spring01.model.dto.MemberDTO;
import com.example.spring01.service.MemberService;

/*
 *  1.@Controller 클래스 생성 
            |
 *  2.@RequestMapping 어노테이션을 이용해서 처리할 요청 경로를 지정(해당 경로로 요청시 메소드 실행.)         
            |
 *  3.웹 브라우저의 요청을 처리할 메서드를 구현(본격적으로 처리할 로직을 짜면 된디.)
            |
 *  4.뷰 이름 리턴
 */

// 스프링에서 관리하는 컨트롤러 빈으로 등록
@Controller 
public class MemberController {

    // MemberService 인스턴스를 주입시킴
    @Inject
    MemberService memberService;

    @RequestMapping("member/list.do") // url mapping
    public String memberList(Model model) {

        List<MemberDTO> list = memberService.memberList();

        // Map, Model은 인터페이스 이므로 인터페이스를 구현한 클래스의 객체를 생성해서 리턴
        // "list" = ${list} model로 전달하는 list와 jsp의 ${list}는 같다(mapping)
        model.addAttribute("list", list);

        // WEB-INF/views/member/member_list.jsp
        return "member/member_list";
    }

    @RequestMapping("member/write.do") // url mapping
    public String write() {

        // WEB-INF/views/member/memebr_write.jsp로 포워딩
        return "member/write";
    }

    // @ModelAttribute : 폼에서 전달된 값을 저장하는 객체
    @RequestMapping("member/insert.do")
    public String insert(@ModelAttribute MemberDTO dto) {

        // System.out.println(dto);
        memberService.insertMember(dto);

        return "redirect:/member/list.do";
    }

    // @RequestParam : request.getParameter("변수명") 대체
    @RequestMapping("member/view.do")
    public String view(@RequestParam String userid, Model model) {

        // 모델에 자료 저장
        model.addAttribute("dto", memberService.viewMember(userid));

        // view.jsp로 이동
        return "member/view";
    }

    // java.util.Date
    @RequestMapping("member/update.do")
    public String update(MemberDTO dto, Model model) {

        // 비밀번호 체크
        boolean result = memberService.checkPw(dto.getUserid(), dto.getPasswd());
        
        if(result) {

            // 회원정보 수정
            memberService.updateMember(dto);

            // 수정 후 목록으로 이동
            return "redirect:/member/list.do"; // redirect

        } else { // 비밀번호가 틀리면
            model.addAttribute("dto", dto);
            model.addAttribute("join_date", memberService.viewMember(dto.getUserid()).getJoin_date());
            model.addAttribute("message", "비밀번호를 확인하세요.");

            return "member/view"; // forward
        }

        @RequestMapping("memeber/delete.do") // requestMapping 어노테이션 이용시, 요청 경로 지정(해당 경로로 요청시 메서드 실행)
        public String delete(String userid, String passwd, Model model) {

            // boolean 으로 참, 거짓 = 서비스(비밀번호 확인) 메서드 (필요한 인자 값 param1, param2)
            boolean result = memberService.checkPw(userid, passwd);

            if(result) { // 비번이 맞으면 삭제 => 목록으로 이동

                // service 클래스의 deletMember 메소드 호출
                memberService.deleteMember(userid);

                // list로 이동
                return "redirect:/member/list.do";

            } else { // 비번이 틀리면 되돌아감

                model.addAttribute("message", "비밀번호를 확인하세요.");
                model.addAttribute("dto", memberService.viewMember(userid));

                // view로 이동
                return "member/view";
            }
        }
    }
}