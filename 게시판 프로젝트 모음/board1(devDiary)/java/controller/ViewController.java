
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company01.springEx01.logic.Board;
import com.company01.springEx01.service.BoardService;

import org.apache.log4j.Logger;

@Controller
@RequestMapping("view/*") // URL에 view라는 요청이 들어오면 무조건 ViewController.java로 보내겟다.
public class ViewController {

    Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    BoardService boardService;

    // 게시판 목록
    @RequestMapping("view/dashboard") // URL에 view라는 요청이 들어오면 dashboard()라는 메소드로 보내겟다.
    public ModelAndView dashboard() {

        List<Board> result = boardService.getBoardList(); 

		log.info(result);

        ModelAndView mav = new ModelAndView();

        return  mav;
    }

    // 글 작성
    @RequestMapping(value="view/boardwrite", method=RequestMethod.GET)
    public ModelAndView boardwrite() {

        ModelAndView mav = new ModelAndView();

        return mav;
    }

    // 글 내용
    @RequestMapping(value="view/boardDetail", method=RequestMethod.GET)
    public ModelAndView boardDetail(int id) { // get으로 넘어오는 "id" 매개변수를 받는다.

        // viewUpdate : 조회수 추가 메서드
        boardService.viewsUpdate(id);

        // getBoardDetail : 특정 글 하나의 데이터를 조회하는 메서드
        Board result = boardService.getBoardDetail(id);

        ModelAndView mav = new ModelAndView();

        mav.addObject("result", result);

        return mav;
    }
}