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
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    
    @Autowired
    // BoardService.java 인터페이스를 의존성 주입.
    private BoardService bservice;

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

        /* 리다이렉트 방식으로 목록페이지로 이동하는 이유 '등록', '수정', '삭제',와 같은 작업이 처리가 된 후
           "새로고침"을 통해 동일한 내용을 계속 서버에 등록할 수 없게 하기 위함.
        */
        return "redirec:/board/list";

    }

    /* 게시판 조회 */
    @GetMapping("/get")
    // View로 부터 '게시판 번호'를 전달받기 위해 int형 변수를 파라미터로 추가
    // '게시판 조회'페이지에 쿼리 실행 후 전달받는 BoardVO 객체 데이터를 전달하기 위해 Model을 파라미터로 추가
    public void boardGetPageGET(int bno, Model model, Criteria cri) {

        /*
          addAttribute 메소드를 호출하여 "pageInfo" 속성명에 BoardService 인터페이스의 getPage()
          메소드 호출하여 반환받은 BoardVO 객체를 속성 값으로 저장한다.
         */
        model.addAttribute("pageInfo", bservice.getPage(bno));

        // 'cri' 속성명에 속성값으로 뷰로부터 전달받은 Criteria 인스턴스를 저장한다.
        model.addAttribute("cri", cri);
    }

    /* 수정 페이지 이동 */
    /* 
      '게시판 조회(get.jsp'에서 '수정' 버튼을 클릭 했을 때 '수정 페이지(modify.jsp)'로 이동이 되고, 내용을 변경 후
      '수정 완료' 버튼을 눌렀을때 DB에 저장된 기존의 데이터가 새로운데이터로 업데이트하는 기능을 목표로 한다.
      수정이 완료된 후 '목록 페이지(list.jsp)'로 이동 후, 수정이 완료되었다는 경고창을 뜨도록 할 것이다. 
    */
    @GetMapping("/modify")
    public void boardModifyGET(int bno, Model model, Criteria cri) {

        /*
           '수정 페이지 이동' 메소드는 '조회 페이지 이동' 메소드와 동일하다. 수정하고자 하는 게시판의
         내용을 출력해야 하기 때문이다. 따라서 int 형 파라미터와 해당 게시판의 내용을 호출하는 Service메소드(getPage())를 호출
         */
        model.addAttribute("pageInfo", bservice.getPage(bno));

        /*
         /board/modify uri 매핑 메소드가 조회 화면(get.jsp)에서 pageNum, amount 데이터를 수집할 수 있도록 Criteria cri 추가
         그리고 전달받은 데이터를 다시 '수정 화면(modify.jsp)'에 전송하도록 addAttribute 메소드를 사용하여 "cri" 속성명에 Criteria 인스턴스를 저장
         */
        model.addAttribute("cri", cri);

    }

    /* 페이지 수정 */
    @PostMapping("/modify")
    public String boardModifyPOST(Board board, RedirectAttribute rttr) {

        // service 클래스를 대표하는 변수(이름)을 bservice로 설정한 변수를 셋팅하고, service의 modify메소드를 불러오고, 수정될 내용의 데이터를 가져오기 위해 (board) 입력
        bservice.modify(board);

        // list.jsp 페이지 이동 시 수정이 완료되었음을 알리는 경고창을 띄우기 위해 "modify success" string 데이터를 "result" 속성 값에 저장하는 addFlashAttribute() 메소드 호출
        rttr.addFlashAttribute("result", "modify suceess");

        // 리턴 타입은 String 타입으로 작성하였고, 리다이렉트 방식으로 리스트 페이지 이동을 하도록 작성
        return "redirect:/bodr/list";

    }

    /* 페이지 삭제 */
    @PostMapping("/delete")
    /*  
        board : 삭제 쿼리를 실행하기 위해선 게시판 번호(bno)에 대한 정보가 필요로 하기 때문에 int형 변수를 파라미터로 부여하였고
        rttr  : 수정 기능 실행 후 리다이렉트 방식으로 리스트 페이지 이동시 데이터를 같이 전송하기 위해서 RedirctAttributes 객체를 파라미터로 부여합니다
    */
    public String boardDeletePOST(int bno, RedirectAttributes rttr) {

        // 삭제된 내용의 데이터를 가져오는 로직
        bservice.delete(bno);

        // list.jsp 페이지 이동 시 삭제가 완료되었음을 알리는 경고창을 띄우기 위해 "delete success"  String 데이터를 "result" 속성 값에 저장하는 addFlashAttribute() 메소드 호출
        rttr.addFlashAttribute("result", "delete success");

        // 리턴 타입은 String 타입으로 작성하였고, 리다이렉트(Redirect) 방식으로 리스트 페이지 이름을 이동을 하도록 작성
        return "redirect:/board/list";
    }

    /* 게시판 목록 페이지 접속(페이징 적용) */
    @GetMapping("list")
    public void boardListGET(Model model, Criteria cri) {

        log.info("boardListGET");

        // PageMakerDTO 클래스의 데이터를 뷰(View)로 보내기 위해 addAttribute() 메소드를 활용하여 'pageMaker' 속성에 저장
        model.addAttribute("list", bservice.getListPaging(cri));

        // BoardController.java에서 BoardService의 getTotal() 메서드를 호출하는 코드에 인자 값을 추가해준다.
        int total = bservice.getTotal(cri);

        PageMakerDTO pageMake = new PageMakerDTO(cri, total);

        model.addAttribute("pageMake", pageMake)
    }
}
