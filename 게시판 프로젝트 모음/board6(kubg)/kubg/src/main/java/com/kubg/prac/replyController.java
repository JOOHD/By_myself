package com.kubg.prac;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/reply/*")
public class replyController { 
    
    @Inject
    ReplyService ReplyService;

    // 댓글 입력
    @RequestMapping("insert.do") // 로그인 여부 확인을 위해 session 추가, 페이징처리 작업 추가, ModelAndView로 작업처리
    public void insert(@ModelAttribute ReplyVO vo, HttpSession session) {

        String userId = (String) session.getAttribute("userId");
        vo.setReplyer(userId);
        replyService.create(vo);
    }

    // 댓글 목록(@Controller방식 : view(화면)를 리턴)
    @RequestMapping("list.do")
    public ModelAndView list(@RequestParam int bno,
                             @RequestParam(defaultValue = "1") int curPage,
                             ModelAndView mav,
                             HttpSession session){
    
        // 페이징 처리
        int count = replyService.count(bno); // 댓글 갯수
        ReplyPager replyPager = new ReplyPager(count, curPage);
        int start = replyPager.getPageBegin();
        int end = replyPager.getPageEnd();
        List<ReplyVO> list = replyService.list(bno, start, end, session);

        // 뷰이름 지정
        mav.setViewName("board/replyList");

        // 뷰에 전달할 데이터 지정
        mav.addObject("list", list);
        mav.addObject("replyPager", replyPager);

        // replyList.jsp로 포워딩
        return mav;
    }

    //----------------------------------------------------------------------------------------
    @Service
    public class ReplyServiceImpl implements ReplyService {

        @Inject
        ReplyDAO replyDAO;

        // 댓글 목록
        @Override
        public List<ReplyVO> list(Integer bno, int start, int end, HttpSession session) {
            List<ReplyVO> items = replyDAO.list(bno, start, end);

            // 세션에서 현재 사용자 id값 저장
            String userId = (String) session.getAttribute("userId");

            for(ReplyVO vo : items) {

                // 댓글 목록중에 비밀 댓글이 있을 경우
                if(vo.getSecreatReply().equals("y")){
                    if(userId == null) { // 비로그인 상태면 비밀 댓글로 처리
                        vo.setReplytext("비밀 댓글입니다.");
                    } else {             // 로그인 상태일 경우
                        String writer = vo.getWriter(); // 게시물 작성자 저장
                        String replyer = vo.getReplyer(); // 댓글 작성자 저장

                        // 로그인한 사용자가 게시물의 작성자x, 댓글 작성자도x 비밀댓글로 처리
                        if(!userId.equals(writer) && !userId.equals(replyer)) {
                            vo.setReplytext("비밀 댓글입니다.");
                        }
                    }
                }
            }
        }
    }
}
