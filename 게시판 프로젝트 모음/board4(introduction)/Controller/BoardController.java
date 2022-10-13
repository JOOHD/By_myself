import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.board.example.dto.BoardDTO;
import com.board.example.service.BoardService;
import com.sun.media.jfxmedia.logging.Logger;

@Controller
@RequestMapping("/board/*")
public class BoardController {
    
    @Inject
    BoardService boardService;

    /* 과거 ModelAndView를 활용한 방법
      @RequestMapping("list.do")
      public ModelAndView boardMenu() throws Exception {

        List<BoardDTO> list = boardService.boarList();
        
        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("board/board_list");
        mav.addObject("list", list);

        // board/board_list.jsp로 이동
        return mav; 
      }
     */

     // 현재 자주 쓰는 Model 클래스를 DI 하는 방법
     @RequestMapping("list.do")
     public String boardList(Model model) throws Exception {

        // list 변수에 결과 값을 담는다.
        List<BoardDTO> list = boardService.boardList();

        // model에 데이터 값을 담는다.
        model.addAttribute("list", list); 

        // board/board_list.jsp로 이동
        return "board/board_list";
     }

     // writer_page.jsp 매핑
     @ReqeustMapping("writer_page")
     public String writerpage() {

        return "board/wirter_page;"
     }

     // 게시글 from 데이터 처리
     @RequestMapping(value="insert.do", method=RequestMethod.POST)
     public String boardWriter(BoardDTO bdto) throws Exception {

        boardService.writerBoard(bdto);

        return "redirect:list.do";
     }

     // 게시글 상세내용 불러오기
     @RequestMapping(value="read.do", method=RequestMethod.GET) // GET방식으로 통신을 할 것
     // 프론트단에서 파라미터값으로 넘어오는 값을 받기 위해 설정(@RequestParam int bno)
     public String boardRead(@RequestParam int bno, Model model) throws Exception {
      
         // 서비스 메소드를 호출하여 bno 값을 넘겨준다. 
         BoardDTO data = boardService.boardRead(bno); 

         // model에 데이터 값을 담는다.
         model.addAttribute("data", data); 

         // board/baord_list.jsp 이동
         return "board/board_read";
     }

     // 게시글 수정 실행
     @RequestMapping(value="update.do", method=ReqeustMethod.POST)
     public String boardUpdatedo(BoardDTO bdto) throws Exception {

         boardService.updateBoard(bdto);

         // 리스트로 리다이렉트
         return "redirect:list.do"; 
     }

     // 게시글 삭제 실행
     @RequestMapping(value="delete.do", method=ReqeustMethod.GET)
     public String boardDelete(@RequestParam int bno) throws Exception {

         boardService.deleteBoard(bno);

         // 리스트로 리다이렉트
         return "redirect:list.do"; 
     }
}
