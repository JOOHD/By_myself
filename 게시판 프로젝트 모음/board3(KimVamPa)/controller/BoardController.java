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
@RequestMapping("/board/*") // /board로 시작하는 모든 처리를 BoardController.java가 하도록 지정
public class BoardController {
    
    // lombok 라이브러리가 추가되어 있는 경우 @log4j 어노테이션을 추가하면 코드 없이 log 메서드 사용가능
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    /*
      뷰로부터 전송받은 데이터를  데이터베이스 vam_board 테이블에 데이터를 삽입 쿼리를 실행하는 
      Mapper, Service 메서드를 작성하였기 때문에 해당 메서드를 호출하고 단지 뷰로부터 전달받은 데이터를 
      파라미터 부여하여 넘겨주기만 하면 된다.

      그러기 앞서 BoardService.java 인터페이스를 의존성 주입한다.
     */
    @Autowired
    private BoardService bservice;

    /* 게시판 목록 페이지 접속 
    @GetMapping("/list")
    // => @RequestMapping(value="list", method=RequestMethod.GET)
    // View에 데이터를 전송하기 위해 Model 파라미터를 추가
    public void boardListGET(Model model) {

        log.info("게시판 목록 페이지 진입");

        // "list"라는 속성명에 BoardService 클래스의 getList() 메소드를 반환 값(게시판 목록 데이터)을 속성 값으로 저장
        model.addAttribute("list", bservice.getList());

    }
    */

    /* 게시판 목록 페이지 접속(페이징 적용) */
    @GetMapping("/list")
    // => @RequestMapping(value="list", method=RequestMethod.GET)
    // Criteria 클래스를 파라미터로 추가 (보고자 하는 페이지의 정보를 얻기 위해서)
    public void boardListGET(Model model, Criteria cri) {

        // 게시판 목록 페이지 접속 로그
        log.info("boardListGET");

        log.info("cri : " + cri);

        /* 
           "list"라는 속성명에 BoardService 클래스의 getList() 메소드를 반환 값(게시판 목록 데이터)을 속성 값으로 저장
           기존에 사용하던 getList() 메소드 대신 새로 작성한 getListPaging() 메소드를 사용 
        
           PageMakerDTO 클래스의 데이터를 뷰(View)로 보내기 위해 addAttribute() 메소드를 활용하여 'pageMaker' 속성에 저장
        */
        model.addAttribute("list", bservice.getListPaging(cri));

        // BoardController.java에서 BoardService의 getTotal() 메서드를 호출하는 코드에 인자 값을 추가해준다.
        int total = bservice.getTotal(cri);
    
        PageMakerDTO pageMaker = new PageMakerDTO(cri, total);

        model.addAttribute("pageMaker", pageMaker);

    }

    /* 게시판 등록 페이지 접속 */
    @GetMapping("/enroll")
    // => @RequestMapping(value="enroll", method=RequestMethod.GET)
    public void boardEnrollGET() {

        log.info("게시판 등록 페이지 진입");

    }
    
    /* 게시판 등록 */
    @PostMapping("/enroll")
    /* 
      뷰가 전송하는 데이터를 전송받기 위해 BoardVO 클래스를 파라미터로 작성한다. Board 객체생성 (feat. 게시글)

      게시판 등록 기능 수행을 완료했기에 '게시판 등록' 페이지에 계속 있을 필요가 없다. 따라서 목록 페이지로 이동 되도록
      처리를 해주어야한다. 먼저 boardEnrollPOST(게시판 등록 기능 수행) 메서드 리턴 방식을 String으로 변경 한 후 리다이렉트
      방식으로 '게시판 목록' 페이지로 이동하도록 리턴값을 작성한다.
    */
    public String boardEnrollPOST(BoardVO board, RedirectAttributes rttr) {

        // BoardVO 클래스에 데이터가 의도대로 저장되었는지를 확인하기 위해 LOG 작성
        log.info("BoardVO : " + board);

        // 등록한 데이터를 가져오는 로직
        bservice.enroll(board);

        log.info("BoardVO : " + board);

        /*
          게시판 목록 화면으로 이동시에 등록 성공 여부를 알리는 문자를 같이 전송하기 위해
          addFlashAttribute() : 일회성으로만 데이터를 전달하기 위함 '게시판 목록' 페이지에서 자바스크립트를 통해
          서버로부터 전달받은 데이터가 있을 경우 경고창을 뜨도록 로직을 구성할 것인데, 전달받은 데이터가 계속 잔존할
          경우 경고창이 계속 뜰 수 있기 때문이다.
         */
        rttr.addFlashAttribute("result", "enroll success");

        /* 리다이렉트 방식으로 목록페이지로 이동하는 이유는 '등록', '수정', '삭제' 와같은 작업이 처리가 된 후 
          "새로고침"을 통해 동일한 내용을 계속 서버에 등록할 수 없게 하기 위함  */
        return "redirect:/board/list";

    }

    /* 게시판 조회 */
    @GetMapping("/get")
    /*
      View로 부터 '게시판 번호'를 전달받기 위해 int형 변수를 파라민터로 추가한다.
      더불어 '게시판 조회' 페이지에 쿼리 실행 후 전달받는 BoardVO 객체 데이터를 전달하기 위해
      Model을 파라미터로 추가한다. 

      board/get 매핑 메서드에 view에서 전송하는 pageNum, amount 데이터를 사용할 수 있도록
      파라미터를 추가해야한다. pageNum 과 amount는 Criteria 클래스에 정의되어 있는 변수들이기 때문에,
      두 데이터를 같이 가져오기 위해 Criteria 클래스를 파라미터로 부여한다.
     */
    public void boardGetPageGET(int bno, Model model, Criteria cri) {

        /* 
          addAttribute 메소드를 호출하여 "pageInfo" 속성명에 BoardService 인터페이스의 getPage()
          메소드 호출하여 반환받은 BoardVO 객체를 속성 값으로 저장한다.
        */
        model.addAttribute("pageInfo", bservice.getPage(bno));

        // 'cri' 속셩명에 속성값으로 뷰로부터 전달받은 Criteria 인스턴스를 저장한다.
        model.addAttribute("cri", cri);
    }

    /* 수정 페이지 이동 
         1.조회 페이지(get.jsp)에서 수정 페이지(modify.jsp)로 이동할 수 있도록 해주는 메소드.

         '수정 페이지 이동' 메소드는 '조회 페이지 이동' 메소드와 동일하다. 수정하고자 하는 게시판의
         내용을 출력해야 하기 때문이다. 따라서 int 형 파라미터와 해당 게시판의 내용을 호출하는 Service메소드(getPage())를 호출

         2./board/modify uri 매핑 메소드가 조회 화면(get.jsp)에서 pageNum, amount 데이터를 수집할 수 있도록 Criteria cri 추가
         그리고 전달받은 데이터를 다시 '수정 화면(modify.jsp)'에 전송하도록 addAttribute 메소드를 사용하여 "cri" 속성명에 Criteria 인스턴스를 저장
    */     
    @GetMapping("/modify")
    public void boardModifyGET(int bno, Model model, Criteria cri) {

        model.addAttribute("pageInfo", bservice.getPage(bno));

        model.addAttribute("cri", cri);

    }

    /*  페이지 수정 
        2.수정 페이지(modify.jsp)에서 내용을 변경 후 "수정 완료" 버튼을 눌렀을 때 실행되는 메소드
    */
    @PostMapping("/modify")
    /*  
        board : 수정될 내용의 데이터를 가져오기 위해 BoardVO 클래스를 파라미터로 부여
        rttr  : 수정 기능 실행 후 리다이렉트 방식으로 리스트페이지 이동시 데이터를 같이 전송하기 위해서 RedirectAttributes 객체를 파라미터로 부여
    */
    public String boardModifyPOST(BoardVO board, RedirectAttributes rttr) {

        // 수정된 내용의 데이터 가져오는 로직
        bservice.modify(board);

        // list.jsp 페이지 이동 시 수정이 완료되었음을 알리는 경고창을 띄우기 위해 "modify success" string 데이터를 "result" 속성 값에 저장하는 addFlashAttribute() 메소드 호출
        rttr.addFlashAttribute("result", "modify success");

        // 리턴 타입은 String 타입으로 작성하였고, 리다이렉트 방식으로 리스트 페이지 이동을 하도록 작성
        return "redirect:/board/list";

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

        // list.jsp 페이지 이동 시 삭제가 완료되었음을 알리는 경고창을 띄우기 위해 "delete success" String 데이터를 "result" 속성 값에 저장하는 addFlashAttribute() 메소드 호출
        rttr.addFlashAttribute("result", "delete success");

        // 리턴 타입은 String 타입으로 작성하였고, 리다이렉트(Redirect) 방식으로 리스트 페이지 이동을 하도록 작성하였습니다.
        return "redirect:/baord/list";

    }

}
