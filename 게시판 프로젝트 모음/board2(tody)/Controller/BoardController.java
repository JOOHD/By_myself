import java.util.List; 
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tody.common.common.CommandMap;
/*
   CommandMap 클래스는 내부적으로 Map을 하나 생성하고, 그 맵에 모든 데이터를 담는 역할을 한다.
   중요한 것은 절대. Map을 상속받아서는 안된다. 많은 메서드들 때문에 어려워 보일텐데, 거의 대부분은
   Map의 기본기능을 다시 호출하는 작업들이다. 여러곳에서 CommandMap을 Map과 똑같이 사용할 수 있게 만들어 두었다.
 */
import tody.prj.service.BoardService;

@Controller // Controller 객체임을 선언
public class BoardController {

    Logger log = Logger.getLogger(this.getClass());

    // bean등록을 수동으로하고, 그 빈의 이름을 "boardService"라 정한다. Controller에서 service에 접근하기 위해
    @Resource(name="boardService") 
    private BoardService boardService;

    // 게시판 목록
    @RequestMapping(value="/board/boardList") // 요청 URL을 알려준다.
    // public ModelAndView openBoardList(CommandMap commandMap) throws Exception 
       public ModelAndView openBoardList(Criteria cri) throws Exception { // 현재 페이지 번호와 페이지당 보여줄 게시글 수가 담긴 Criteria 객체를 사용

        // 우리가 화면에 보여줄 JSP를 의미한다. 파일 경로를 써주면 된다.
        ModelAndView mv = new ModelAndView("/board/boardList"); 

        // PageMaker() 객체를 생성한다.
        PageMaker pageMaker = new PageMaker();
        // page와 perPageNum을 셋팅해준다.
        pageMaker.setCri(cri);
        // 총 게시글의 수를 셋팅. 아직 총 게시글 수를 조회하는 로직은 구현하지 않아서 db에 있는 총 게시글의 수인 100을 넣었다.
        // pageMaker.setTotalCount(100);
        pageMaker.setTotalCount(boardService.countBoardListTotal()); // 100으로 셋팅한 부분에 총 게시글 수를 구하는 로직을 넣었다.

        /*
          게시판 목록을 저장하는 List를 선언한다. List 형식은 Map<String, Object> 이다.
          key와 Value의 형태로 각 게시글의 정보들이 저장된다. boardService.selectBoardList(commandMap)은
          게시글 목록을 조회하는 비즈니스 로직을 호출한다. 이 메서드를 통해 얻어온 결과를 "list"라는 이름에 저장 
         */

        // 원래의 목록 조회 로직에서 Criteria 파라미터를 사용하기 위해 수정
        List<Map<String, Object>> list = boardService.selectBoardList(cri); 
    //  List<Map<String, Object>> list = boardService.selectBoardList(commandMap); 
    //  List<BoardDto> list = boardService.selectBoardList(); Service를 호출하는 부분이다. 반환값르 list에 저장

        // 서비스 로직의 결과(34행의 list에 담긴 정보)를 ModelAndView 객체에 담아서 jsp에 그 결과를 전송
        mv.addObject("list", list); 
        // 셋팅된 pageMaker에는 페이징을 위한 버튼의 값들이 들어있고 ModelAndView를 이용해 jsp에 넘겨준다.
        mv.addObject("pageMaker", pageMaker);

        return mv;
    }

    // 글 작성
    @RequestMapping(value="/board/boardWrite")
    public String boardWrite() throws Exception {
        return "/board/boardWrite";
    }

    // 글 등록
    @RequestMapping(value="/board/boardInsert") // form 태그의 action 속성에 있는 url이다.
    // form 태그의 파라미터를  CommandMap으로 받음. BoardVO를 만들어 VO형태로 받아도 된다.
    public ModelAndView boardInsert(CommandMap commandMap) throws Exception {
        // 컨트롤러가 끝나고 보내질 페이지이다. 등록을 완료하면 게시글 목록으로 다시 이동시키려고 리다이렉트를 사용
        ModelAndView mv = new ModelAndView("redirect:/board/boardInsert");
        // 컨트롤러에 들어온 파라미터를 게시판 DB에 등록한다.
        boardService.insertBoard(commandMap);

        return mv;
    }

    // 글 상세페이지
    @RequestMapping(value="/board/boardDetail")
    // 주소창에 파라미터를 날린 것을 CommandMap 형으로 받았다.
    // 이전 목록에 대한 정보를 Criteria 클래스에 담는다.
    public ModelAndView boardDetail(CommandMap commandMap, Criteria cri) throws Exception {

        // 상세페이지로 갈 주소이다.
        ModelAndView mv = new ModelAndView("/board/boardDetail");
        // 글의 상세 조회를 위한 서비스를 호출한다. 글 목록은 리스트형태이지만, 글 상세정보는 한 줄만 가져오면 되기에 map 형식이다. 
        Map<String, Object> detail = boardService.viewBoardDetail(commandMap.getMap()); //  맵(한줄)
    //  List<Map<String, Object>> list = boardService.selectBoardList(cri); 리스트(여러줄)

        // 상세페이지로 갈 때, 가져갈 글 목록 리스트이다. "detail"이란 이름으로 담았다.
        mv.addObject("detail", detail);

        // PageMaker 인스턴스를 생성하고 현재 페이지와 함께 view로 던진다.
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        mv.addObject("page", cri.getPage());
        mv.addObject("pageMaker", pageMaker);

        return mv;
    }

    // 글 수정하기
    @RequestMapping(value="/board/baordUpdate") // 수정페이지를 보여주는 컨트롤러이다.
    public ModelAndView boardUpdate(CommandMap commandMap, Criteria cri) throws Exception {

        ModelAndView mv = new ModelAndView("/board/boardUpdate");
        // 글의 상세정보를 가져와 "detail"이란 이름에 저장시킨다.
        Map<String, Object> detail = boardService.selectBoardDetail(commandMap.getMap()); 
        mv.addObject("detail", detail);

        // PageMaker 인스턴스를 생성하고 현재 페이지와 함께 view로 던진다.
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);
        mv.addObject("page", cri.getPage());
        mv.addObject("pageMaker", pageMaker);

        return mv;
    }

    // 글 수정 후, 상세 페이지로 
    @RequestMapping(value="/board/boardUpdate", method=RequestMethod.POST) // POST형태인 /board/boardUpdate를 뜻한다.
    // 이전 목록에 대한 정보를 Criteria 클래스에 담고, 리다이렉트를 위해 RedirectAttributes 를 사용한다.
    public ModelAndView boardUpdatePOST(CommandMap commandMap, Criteria cri, RedirectAttribute redAttr) throws Exception {

        // 수정 후, 상세 페이지로 리다이렉트 시킨다. 여기서 상세페이지로 가기 위해선 글번호가 필요하기 때문에 글 번호 파라미터도 같이 넘겨준다.
        ModelAndView mv = new ModelAndView("redirect:/board/boardDetail");
        mv.addObject("idx", commandMap.get("idx"));
        // 수정한 글을 update 시키는 부분이다.
        boardService.updateBoard(commandMap.getMap());

        // 페이지 정보를 리다이렉트해준다.
        redAttr.addAttribute("page", cri.getPage());
        redAttr.addAttribute("perPageNum", cri.getPerPageNum());

        return mv;
    }

    // 글 삭제
    @RequestMapping(value="/board/boardDelete")
    // 이전 목록에 대한 정보를 Criteria 클래스에 담고, 리다이렉트를 위해 RedirectAttributes 를 사용한다.
    public ModelAndView boardDelete(CommandMap commandMap, Criteria cri, RedirectAttribute redAttr) throws Exception {
        // 삭제 후에 목록으로 리다이렉트 시켜준다.
        ModelAndView mv = new ModelAndView("redirect:/board/boardList");
        // 파라미터가 잘 왔는지 확인하려고 만든거다. 생략 해도 상관 없다.
        boadrService.deleteBoard(commandMap.getMap());

        // 페이지 정보를 리다이렉트해준다.
        redAttr.addAttribute("page", cri.getPage());
        redAttr.addAttribute("perPagNum", cri.getPerPageNum());

        // 삭제 해주는 부분이다.
        return mv;
    }
}

/*
  
@Controller
@RequestMapping("/contact")
//@Slf4j
public class contactController {
    
    @Autowired // inject와 비슷하지만, 찾는 순서가 다름. autowired는 타입 > 이름 > qualifier 이고 inject는 타입 > qulifier > 이름
    private ContactService ContactService;

    @GetMapping // 견적문의
    public ModelAndView getContact() {

        List<ContactVO> contactVOList = contactService.getContact();

        ModelAndView mav = new ModelAndView();
        mav.addObject("contacts", contactVOList);
        mav.setViewName("contact");

        return mav;
    }
}

 */