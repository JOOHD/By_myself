package prac;

public class cartController {
    
    // 장바구니
    @ResponseBody
    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public int cart(HttpSession session, HttpServletReqeust request, HttpServletResponse response, CartVO cartVO) throws Exception {
        logger.info("itemno =" + cartVO.getCart_item_no());

        Cookie cookie = WebUtils.getCookie(request, "cartCookie");

        // 비회원장바구니 첫 클릭시 쿠키생성
        if(cookie == null && session.getAttribute("member") == null) {
            String ckid = RandomStringUtils.random(6, true, true);

            Cookie cartCookie = new Cookie("cartCookie", ckid);

            cartCookie.setPath("/");
            cartCookie.setMaxAge(60 * 60 * 24 * 1);
            response.addCookie(cartCookie);
            cartVO.setCart_ckid(ckid);
            this.mainService.cartInsert(cartVO);

        // 비회원이 쿠키가 이쓴ㄴ 상태로 추가를 눌렀을때 (장바구니 쿠키생성 후 상품추가)
        } else if (cookie != null && session.getAttribute("member") == null) {

            String ckValue = cookie.getValue();
            cartVO.setCart_ckid(ckValue);
            
            // 장바구니 중복제한
            if(mainService.cartCheck(cartVO) != 0) {

                return 2;
            }

            // 쿠키 시간 재설정해주기
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 1);
            response.addCookie(cookie);

            mainService.cartIsert(cartVO);

        // 회원이 장바구니 추가를 눌렀을때    
        } else if(session.getAttribute("member") != null) {
            MemberVO memberVO = (MemberVO)session.getAttribute("member");
            cartVO.setCart_mem_no(memberVO.getMEM_NO());

            if(mainService.cartMemCheck(cartVO) != 0) {

                return 1;
            }
        }
        
    }

    // 로그인 POST
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public String login(MemberVO vo, HttpSession session, HttpServletRequest request, HttpServletResponse response,
            RedirectAttributes rttr, Model model) throws Exception {

        MemberVO login = service.login(vo);

        boolean pwdMatch = pwdEncoder.matches(vo.getMEM_PW(), login.getMEM_PW());

            // 비회원 장바구니 회원장바구니로 이동
            Cookie cookie = WebUtils.getCookie(request, "cartCookie");

            if (cookie != null) {
                String ckValue = cookie.getValue();
                logger.info("비회원장바구니 삭제");
            //쿠키에 담긴 정보에 회원NO 입력

                mainService.cartUpdate(login.getMEM_NO(), ckValue);
            //쿠키삭제
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            return "/main/index";
    }
}
