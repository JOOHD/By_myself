package com.kubg.prac;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kubg.domain.CartListVO;
import com.kubg.domain.CartVO;
import com.kubg.domain.GoodsViewVO;
import com.kubg.domain.MemberVO;
import com.kubg.domain.OrderDetailVO;
import com.kubg.domain.OrderListVO;
import com.kubg.domain.OrderVO;
import com.kubg.domain.ReplyListVO;
import com.kubg.domain.ReplyVO;
import com.kubg.service.ShopService;

@Controller
@RequestMapping("/shop/*")
public class ShopController {
    
    private static final Logger logger = new LoggerFactory.getLogger(ShopController.class);

    @Inject
    ShopService service;

    // 카테고리별 상품 리스트
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void getList(@RequestParam("c") int cateCode,
                        @RequestParam("l") int level, Model model) throws Exception {
        
        logger.info("get llist");

        List<GoodsViewVO> list = null;
        list = service.list(cateCode, level);

        model.addAllAttributes("list", list);
    }

    // 상품 조회
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public void getView(@RequestParam("n") int gdsNum, Model model) throws Exception {

        logger.info("get view");

        GoodsViewVO view = service.goodsView(gdsNum);
        model.addAttribute("view", view);
    }

    // 상품 소감(댓글) 작성
    @ResponseBody
    @RequestMapping(value = "/view/registReply", method = RequestMethod.POST)
    public void registReply(ReplyVO reply, HttpSession session) throws Exception {

        logger.info("regist reply");

        MemberVO member = (MemberVO)session.getAttribute("member");
        reply.setUserId(member.getUserId());

        service.registReply(reply);
    }

    // 상품 소감(댓글) 목록
    @ResponseBody
    @RequestMapping(value = "/view/replyList", method = RequestMethod.GET)
    public List<ReplyListVO> getReplyList(@RequestParam("n") int gdsNum) throws Exception {

        logger.info("get reply list");

        List<ReplyListVO> reply = service.replyList(gdsNum);

        return reply;
    }

    // 상품 소감(댓글) 삭제
    @ResponseBody
    @RequestMapping(value = "/view/deleteReply", method = RequestMethod.POST)
    public int getReplyList(ReplyVO reply, HttpSession session) throws Exception {

        logger.info("post delete reply");

        // 정상작동 여부를 확인하기 위한 변수
        int result = 0;

        // 현재 로그인한 member 세션을 가져옴
        MemberVO member = (MemberVO)session.getAttribute("member"); 

        // 소감(댓글)을 작성한 사용자의 아이디를 가져옴
        String userId = service.idCheck(reply.getRepNum()); 

        // 로그인한 아이디와, 소감을 작성한 아이디를 비교
         if(member.getUserId().equals(userId)) {

            // 로그인한 아이디가 작성한 아이디를 비교

            reply.setUserId(member.getUserId()); // reply에 userId 저장
            service.deleteReply(reply); // 서비스의 deleteReply 메소드 실행

            // 결과값 변경
            result = 1;
         }

         // 정상적으로 실행되면 소감 삭제가 진행되고, result 값은 1이지만
         // 비정상적으로 실행되면 소감 삭제가 안되고, result 값이 0
         return result;
    }

    // 상품 댓글 삭제(no explain)
    @ResponseBody
    @RequestMapping(value = "/view/deleteReply", method = RequestMethod.POST)
    public int getReplyList(ReplyVO reply, HttpSession session) throws Exception {

        logger.info("post delete reply");

        int result = 0;

        MemberVO member = (MemberVO)session.getAttribute("member");

        String userId = service.idCheck(reply.getRepNum());

        if(member.getUserId().equals(userId)) {

            reply.setUserId(member.getUserId());
            service.deleteReply(reply);

            result = 1;
        }

        return result;
    }

    // 상품 소감(댓글) 수정
    @ResponseBody
    @RequestMapping(value = "/view/modifyReply", method = RequestMethod.POST)
    public int modifyReply(ReplyVO reply, HttpSession session) throws Exception {

        logger.info("modify reply");

        int result = 0;

        MemberVO member = (MemberVO)session.getAttribute("member");
        String userId = service.idCheck(reply.getRepNum());

        if(member.getUserId().equals(userId)) {
        
            service.modifyReply((reply));
            result = 1;
        }

        return result;
    
    }

    // 주문 
    @RequestMapping(value = "/cartList", method = RequestMethod.POST)
    public String order(HttpSession session, OrderVO order, OrderDetailVO orderDetail) throws Exception {
        logger.info("order");

        // 세션 가져오기 (가져올때 다운캐스팅 - 자식 클래스의 타입으로 형변환)
        MemberVO member = (MemberVO)session.getAttribute("member");
        String userId = member.getUserId();

        // 캘랜더 호출
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR); // 연도 추출
        String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1); // 월 추출
        String ymd = ym +  new DecimalFormat("00").format(cal.get(Calendar.DATE)); // 일 추출
        String subNum = ""; // 랜덤 숫자를 저장할 문자열 변수

        for(int i = 1; i <= 6; i++) { // 6회 반복

            subNum += (int)(Math.random() * 10); // 0~9까지의 숫자를 생성하여 subNum에 저장
        }

        String orderId = ymd + "_" + subNum; // [연월일]_[랜덤숫자] 로 구성된 문자

        order.setOrderId(orderId); // 주문 번호
        order.setUserId(userId);   // 주문한 유저 번호

        service.orderInfo(order);  // 주문 정보 메소드 service에서 불러오기 -> dao -> mapper.xml

        orderDetail.setOrderId(orderId); // 주문상세(주문 번호)
        service.orderInfo_Details(orderDetail); // 주문 상세 정보(주문 상세[주문 번호])

        // 주문 테이블, 주문 상세 테이블에 데이터를 전송하고, 카트 비우기
        service.cartAllDelete(userId);

        return "redirect:/shop/orderList";
    }

    // 주문 목록
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public void getOrderList(HttpSession session, OrderVO order, Model model) throws Exception {
        logger.info("get order list");

        MemberVO member = (MemberVO)sessioin.getAttribute("member");
        String userId = member.getUserId();

        order.setUserId(userId);

        List<OrderVO> orderList = service.orderList(order);

        model.addAttribute("orderList", orderList);
    }

    // 주문 상세 목록
    @RequestMapping(value = "/orderView", method = RequesetMethod.GET)
    public void orderInfo_Details(HttpSession session,
                                    @RequestParam("n") String orderId,
                                    OrderVO order, Model model) throws Exception {
        logger.info("get order view");
        
        MemberVO member = (MemberVO)session.getAttribute("member");
        String userId = member.getUserId();

        order.setUserId(userId);
        order.setOrderId(orderId);

        List<OrderListVO> orderView = service.orderView(order);

        model.addAttribute("orderView", orderView);
    }
    
}