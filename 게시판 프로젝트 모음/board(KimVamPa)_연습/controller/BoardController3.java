import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vam.model.BoardVO;
import com.vam.model.Criteria;
import com.vam.model.PageMakerDTO;
import com.vam.service.BoardService;

@Controller
@RequestMapping("/board/*") // board로 시작하는 모든 처리를 BoardController.java가 하도록 지정
public class BoardController3 {
    
    private Logger log = new LoggerFactory.getLogger(Controller.class);

    @Autowired
    private BoardService bservice;

    /* 게시판 목록 페이지 접속(페이징 적용) */
    @GetMapping("/list")
    public void boardListGET(Model model, Criteria cri) {

        // 게시판 목록 페이지 접속 로그
        log.info("boardListGET");

        log.info("cri : " + cri);

        // 게시판 목록 페이지 불러오기
        model.addAttribute("list", bservice.getListPaging(cri));

        // total 변수에 모든 목록(cri) 저장 
        int total = bservice.getTotal(cri);

        PageMakerDTO pageMaker = new PageMakerDTO(cri, total);

        model.addAttribute("pageMaker", pageMaker);
    }

    /* 게시판 등록 페이지 접속 */
    @GetMapping("/enroll")
    public void boardEnrollGET() {

        log.info("게시판 목록 페이지 진입");
    }

    /* 게시판 등록 */
    public String boardEnrollPOST(BoardVO vo, RedirectAttributes rttr) {

        log.info("BoardVO : " + vo);

        bservice.enroll(vo);

        log.info("BoardVO : " + vo);

        rttr.addFlashAttribute("result", "enroll success");;

        return "rediredt:/board/list";
    }

    /* 게시판 조회 */
    @GetMapping("/get")
    public void boardGetPageGET(int bno, Model model, Criteria cri) {

        // 게시판 조회(게시판 번호)
        model.addAttribute("pageInfo", bservice.getPage(bno));

        model.addAttribute("cri", cri);
    }

    /* 게시판 수정 */
    @GetMapping("/modify")
    public void boardModifyGET(int bno, Model model, Criteria cri) {

        model.addAttribute("pageInfo", bservice.getPage(bno));

        model.addAttribute("cri", cri);
    }

    @PostMapping("/modify")
    public String boardModifyPOST(BoardVO board, RedirectAttributes rttr) {

        bservice.modify(board);

        rttr.addFlashAttribute("result", "modify success");

        return "redirect:/board/list";
    }

    /* 페이지 삭제 */
    @PostMapping("/delete")
    public String boardDeletePOST(int bno, RedirectAttributes rttr) {
        
        bservice.delete(bno);

        rttr.addFlashAttribute("result", "delete success");

        return "redirect:/baord/list";

    }

}
