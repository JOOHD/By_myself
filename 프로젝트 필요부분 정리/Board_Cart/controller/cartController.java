package controller;

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

        // 비회원 장바구니 쿠키생성 후 상품추가
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
        } else if(session.getAttribute("member") != null) {
            MemberVO memberVO = (MemberVO)session.getAttribute("member");
            cartVO.setCart_mem_no(memberVO.getMEM_NO());

            if(mainService.cartMemCheck(cartVO) != 0) {

                return 1;
            }
        } 

        /*
        비동기 처리를 보내기위해 @ResponseBody어노테이션을 붙여줬다.
        리턴타입은 int로 줬다. 
        insert로직에 대한 리턴이기 때문에, ajax의 success에서 받는 리턴값을 String이나 타 값으로 줘도 되겠지만,
        간단하게 장바구니 추가가 완료됐다면 1, 이미있는 상품이라면 2, 그 외에는 3
        이런식으로 처리하기 위해 int로 리턴해줬다.
        이런 방식이 현업 웹개발에서 통용되는 방법인지는 모르겠지만,
        그저 내가 ajax를 사용하며 공부하다보니 이렇게 하는게 간단하고 편하다고 느껴져서 이렇게 구현했다.
        로직은 위에 설명한 mapper의 설명과 같다 ( 당연히.. )
        위에서부터 아래로 차례대로 설명해보자면
        먼저 매개변수는 세션을 확인하기위한 Http Session,
        쿠키를 확인하고, 반환하기 위한 HttpServletReqeust 와 HttpServeltResponse
        그리고 장바구니에 입력될 상품과 선택한 옵션을 받기위한 cartVO다.
        먼저 메서드의 시작은 WebUtils를 사용해 요청값에서 "cartCookie" 라는 key값의 쿠키를 가져온다. 

        그 다음 if문으로 
        - 비회원이 처음 장바구니 추가를 눌렀을때 ( 쿠키생성 )
        - 비회원이 쿠키가 있는 상태로 장바구니 추가를 눌렀을때
        - 회원이 장바구니 추가를 눌렀을 때의 3가지 상황을 나눠주었다.
        */

        //비회원장바구니 첫 클릭시 쿠키생성
        if (cookie == null && session.getAttribute("member") == null) {
            String ckid = RandomStringUtils.random(6, true, true);
            Cookie cartCookie = new Cookie("cartCookie", ckid);
            cartCookie.setPath("/");
            cartCookie.setMaxAge(60 * 60 * 24 * 1);
            response.addCookie(cartCookie);
            cartVO.setCart_ckid(ckid);
            this.mainService.cartInsert(cartVO);
        }    

        /*
        cookie == null, 아까 요청값에서 가져온 "cartCookie"라는 key의 쿠키가 없을때
        AND
        session.getAttribute("member") == null,

        HttpSession으로 세션값을 가져오는데, "member"라는 key값의 세션이 없을때
        (로그인 시 "member"라는 세션key로 세션이 부여되게 구현했다.)

        JAVA으 RandomStringUtils 를 사용해서 6자리값의 난수값을 ckid를 생성해준다.
        new Cookie를 이용해 "cartCookie"를 생성해준다.
        제한 시간은 1일로 생성해줫다.
        그 후 생성한 쿠키를 response에 담아서 브라우저로 보내준다.
        그 다음 cartVO에 ckid를 담아서, 장바구니 insert로직으로 보내준다.

        이때!!!!
        cartVO에 mem_no는 null 상태이다.
        mem_no는 세션에서 가져오는 회원의 PR인데,
        해당 로직은 session이 null일때 들어오는 if문 내 이기 때문이다.
        그렇기 때문에, mapper에서 동적쿼리로 나눠준 mem_no=0일때의 로직을 타게되어
        정상적으로 isnert가 된다.

        그 다음 부분은 쿠키가 생성되었을 때다
        쿠키를 생성해 줄 필요없이, 쿠키의 value값을 ckid에 담아서 insert 헤준다.
        쿠키 제한시간도 1일로 다시 설정해준다.
        
        그리고 장바구니에 중복으로 물건이 담기지 않도록

        <select id="cartCheck" resultType="int">
        SELECT COUNT(*) FROM CART WHERE CART_ckid = #{cart_ckid} 
        AND CART_OPTION_NO = #{cart_option_no}
        </select>
        유효성 검사를 해서 막아주도록 하자.

        이때, 유효성 검사에 걸리면 숫자 2를 return하는데 이것은 아래 ajax로직에서 설명하겟다.

        */

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

        /*
        위의 로직은 로그인부분의 Controller 부분이다.
        필요없는 부분은 전부 삭제하고, 장바구니 부분만 남겼다.
        간단하다. 로그인 로직 사이에 아이디와 비밀번호 확인을 마치고,
        세션을 부여해서 사용자에게 로그인을 시켜주는 부분에
        cartCookie가 존재하는 지 확인 후, 존재한다면 

            <!-- 로그인시 비회원장바구니 -> 회원장바구니 -->
            <update id="cartUpdate">
            UPDATE CART SET cart_mem_no = #{mem_no} WHERE CART_CKID = #{cart_ckid}
            </update>

        장바구니의 mem_no 컬럼에 세션값을 넣어주면 된다.
        위에서 말했지만, 비회원 장바구니 추가시엔 mem_no 컬림이 null로 들어가 있다.
        그 부분에 mem_no를 넣어줘서, 다른 로직없이 update 한번으로 완성시키는 것이다.
        비회원 조회시에는 ckid로 조회를 하고,
        회원 조회시엔 mem_no로 조회를 하도록 조회 부분을 구성하면 
        이런 방식을 사용해도 문제가 없다.  
        */
    }
}

