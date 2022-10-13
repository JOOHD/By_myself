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
// /board로 시작하는 모든 처리를 BoardController.java가 하도록 지정
@RequestMapping("/board/*") 
public class BoardController2 {

    private static final Logger log =LoggerFactory.getLogger(BoardController.class);

    @Autowired
    // BoardService.java 인터페이스를 의존성 주입.
    private BoardService bservice;

    /* 게시판 목록 페이지 접속 */
    @GetMapping("/list")
    // 기존 '게시판 목록 페이지 이동' 메소드(boardListGET)에 뷰에 데이터를 전송하기 위해 Model 파라미터 추가.
    public void boardListGET(Model model) {

        Log.info("게시판 목록 페이지 진입");

        // "list"라는 속성명에 BoardService 클래스의 getList() 메소드를 반환 값(게시판 목록 데이터)을 속성 값으로 저장
        model.addAttribute("list", bservice.getList());
    }

    /* 게시판 등록 페이지 접속 */
    @GetMapping("/enroll")
    public void boardEnrollGET() {

        log.info("게시판 등록 페이지 진입");
    }

    /* 게시판 등록 */
    @PostMapping("/enroll")
    // 뷰가 전송하는 데이터를 전송받기 위해 BoradVO클래스를 파라미터로 작성합니다.
    public String boardEnrollPOST(BoardVO board, RedirectAttributes rttr) {

        // BoardVO 클래스에 데이터가 의도대로 저장되었는지를 확인하기 위해 Log 작성
        log.info("BoardVO : " + board);

        /* 
          해당 메서드에 데이터를 전달 하기 위해서 인자 값으로 뷰로부터 전달받은 BoardVO를 삽입합니다.
          등록된 게시글의 데이터를 가져오는 로직
        */ 
        bservice.enroll(board);

        // 게시판 목록 화면으로 이동시에 등록 성공 여부를 알리는 문자를 같이 전송하기 위해
        rttr.addFlashAttribute("result", "enroll success");

        /*
          리다이렉트 방식으로 목록페이지로 이동하는 이유 '등록', '수정', '삭제',와 같은 작업이 처리가 된 후
          "새로고침"을 통해 동일한 내용을 계속 서버에 등록할 수 없게 하기 위함. 
         */
        return "redirect:/board/list";
        
    }

    /* 수정 페이지 이동 
        조회 페이지 이동 메소드와 동일
    */
    @GetMapping("/modify")
    public void boardModifyGET(int bno, Model model) {
        
        model.addAttribute("pageInfo", bservice.getPage(bno));
        
    }

    /* 게시판 수정 */
    @PostMapping("/modify")
    // 수정될 내용의 데이터를 가져오기 위해 BoardVO 클래스를 파라미터로 부여
    public String boardModifyPOST(BoardVO board, RedirectAttribute rttr) {

        bservice.modify(board);

        rttr.addFlashAttribute("result", "modify success");

        // 리턴 타입은 String, 리다이렉트 방식으로 리스트 페이지 이동을 하도록 작성
        return "redirect:/board/list";

    }

    /* 게시판 삭제 */
    @PostMapping("/delete")
    // 삭제 쿼리를 실행하기 위해선 게시판 번호(bno)에 대한 정보가 필요로 하기 때문에 int형 변수를 파라미터로 부여
    public String boardDeletePOST(int bno, RedirectAttributes rttr) {

        bservice.delete(bno);

        rttr.addFlashAttribute("result", "delete success");

        return "redirect:/boadr/list";

    }

}
