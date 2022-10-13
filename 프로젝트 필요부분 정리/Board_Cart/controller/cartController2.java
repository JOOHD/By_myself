package controller;

public class cartController2 {
    
    /*
      전 글에선 비동기 장바구니 부분의 추가 (insert) 부분을 작성했다.
      이제 장바구니 목록과 삭제 부분을 작성해보려고 한다.
      이번글에선 장바구니 목록(header 부분의 비동기 목록)과
      장바구니 뷰(주문으로 넘어갈 수 있는 장바구니 페이지)를 같이 설명해보겠다.
      
      장바구니 header는 비동기로,
      장바구니 뷰 페이지는 동기 방식으로 구현되었으나, 같은 mapper를 사용한다.
    
      먼저, 구현한 기능이다.
      상품의 옵션별로 장바구니에 추가가 가능하고, 옵션이 중복되면 넣지 못하도록 구현했다.
      장바구니에 추가 된 상품의 가격은 total 부분에 합쳐서 보이도록 했으며,
      삭제하기 버튼을 누르면 해당 옵션이 삭제된다.
     */

     @ResponseBody
     @RequestMapping(value = "/cartHeaderView", method = RequestMethod.GET)
     public List<ItemVO> cartHeaderView(HttpSession session, HttpServletRequeset request, HttpServletResponse response, CartVO cartVO, Model model) throws Exception {

        /*
          ※ 주의하자.웹서버에서 웹 브라우저에 쿠키를 전송할 때, http응답메시지 형태로 보낸다.
          쿠키는 html과 함께 전송된다고 했다.따라서 Html <body>에서 쿠키를 전송하기보다 <head>에서 사용하자.
         */

        // 쿠키는 저장할때, 데이터 뿐만아니라 URL 주소도 함께 쿠키저장도에 저장이 된다.
        Cookie cookie = WebUtils.getCookie(request, "cartCookie"); // 쿠기 생성 request 객체를 사용해서 session 객체에 접근

        // 장바구니 목록(header 부분의 비동기 목록)
        List<ItemVO> list = new ArrayList<>(); // <>(); = 타입 생략 가능, 보통 생성시 타입을 생략해서 작성

        if(cookie != null && session.getAttribute("member") == null) {  // 비회원시 쿠키 value인 ckid 사용

            // getName()은 저장된 데이터 값에 대한 이름을 가져오는 메소드이다.
            // getValue()는 해당 이름이 저장된 데이터 값을 가져온다.
            String cartCookie = cookie.getValue(); // 쿠키는 String 형식의 데이터로 저장해야된다.(10, 20, 같이 숫자여도)
            cartVO.setCart_ckid(cartCookie); // CartVO 클래스의 Cart_ckid메소드에 값을 지정
            list = mainService.cartList(cartVO); // list 참조변수에 mainService.cartList() 호출

        } else if(cookie == null && session.getAttribute("member") != null ) {  // 회원시 회원의 PR인 mem_no를 전달해준다.

            // 회원시 mem_no이용
            logger.info("카트헤더들음??");

            MemberVO memberVO = (MemberVO)session.getAttribute("member");
            cartVO.setCart_mem_no(memberVO.getMEM_NO());
            list = mainService.cartList(cartVO);
        } 

        return list;

     }

     /*
       webUtils
       -WebUtils 클래스를 사용하면 Session에 담겨있는 객체들을 보다 짧은 코드로 넣고 빼고 할 수 있으며, 세션 객체나 쿠키 객체를 받아올 수 있습니다.
        원래는 다음과 같이 Request 객체를 직접 통해서 Session 객체에 접근해야 했습니다.
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");

        그러나 Spring의 WebUtils를 사용하면 . 을 두 개 사용하여 길게 가지 않아도 됩니다.
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");


       장바구니는 쿠키를 이용한다.
       비회원일때는 cookie의 value값을 mapper에 전달해주고,
       회원일때는 회원의 PR인 mem_no를 전달해준다.
       그 결과값을 List에 담아서 뷰의 ajax로 리턴해준다.
       쿠키의 값을 가져오는 부분이나, 세션의 값을 가져오는 부분은 전 글에 작성했으니 생략.
    */

    /*
      장바구니에 담긴 상품이 표시되도록 했으며,
      상품의 수량을 선택할 수 있는데, 상품의 최대 수량이 보이도록 했으며 최대수량 이상은 선택할 수 없도록 했다.
      품절시에는 따로 표시가 되어 선택할 수 없고, 주문 할 수도 없도록 막았다.
      선택한 상품의 수가 표시되고, 그 수에 맞는 가격이 표시된다. 
    */

    /* 장바구니 뷰는 동기식 */
    @RequestMapping(value = "/cartView", method = RequestMethod.GET)
    public String cartView(HttpSession session, HttpServletRequeset request, HttpServletResponse response, CartVO cartVO, Model model) throws Exception {
        Cookie cookie = WebUtils.getCookie(requeset, "cartCookie");

        // 비회원시 쿠기value인 ckid 사용
        if (cookie != null && session.getAttribute("member") == null) {

            String cartCookie = cookie.getValue();
            cartVO.setCart_ckid(cartCookie);
            model.addAttribute("cart", mainService.cartList(cartVO));

          } else if (cookie == null && session.getAttribute("member") != null) {

            //회원시 mem_no이용
            MemberVO memberVO = (MemberVO) session.getAttribute("member");

            cartVO.setCart_mem_no(memberVO.getMEM_NO());
            model.addAttribute("cart", mainService.cartList(cartVO));
            model.addAttribute("cartCount", mainService.cartMemCount(cartVO));
          } 

          return "/main/cartView";
    }

    //장바구니 삭제
    @ResponseBody
    @RequestMapping(value="cartDelete", method = RequestMethod.POST)
    public int cartDelete(HttpSession session, HttpServletRequest request, HttpServletResponse response, CartVO cartVO, Model model) throws Exception{
            mainService.cartDelete(cartVO);
            return 1;
    }
    
    //장바구니 전체삭제
    @ResponseBody
    @RequestMapping(value="cartDeleteAll", method = RequestMethod.POST)
    public int cartDeleteAll(HttpSession session, HttpServletRequest request, HttpServletResponse response, CartVO cartVO, Model model) throws Exception{
          
        //비회원시
        if(session.getAttribute("member") == null) {
            Cookie cookie = WebUtils.getCookie(request, "cartCookie");
            String ckValue = cookie.getValue();
            cartVO.setCart_ckid(ckValue);
            mainService.cartDeleteCk(cartVO);
        }else if(session.getAttribute("member") != null){
            //회원시
            MemberVO memberVO = (MemberVO) session.getAttribute("member");
            cartVO.setCart_mem_no(memberVO.getMEM_NO());
            mainService.cartDeleteMem(cartVO);		  
        }
            return 1;
    }

    // 주문 페이지
    @RequestMapping(value = "/orderView",method=RequestMethod.POST)
    public String orderView(HttpSession session, HttpServletRequest request, HttpServletResponse response,
        OrderItemVO orderItemVO, Model model, int totalPrice, int totalVol) throws Exception{

      List<OrderItemVO> orderList = new ArrayList<>(); 

      for(int i=0; i < (orderItemVO.getItem_no()).length; i++) {
      
        OrderItemVO VO = new OrderItemVO();
        VO.setOrder_item_img(orderItemVO.getItem_img()[i]);         // 상품 이미지
        VO.setOrder_item_name(orderItemVO.getItem_name()[i]);       // 상품명
        VO.setOrder_item_option(orderItemVO.getItem_option()[i]);   // 상품 옵션
        VO.setOrder_item_price(orderItemVO.getItem_price()[i]);     // 상품 가격
        VO.setOrder_option_no(orderItemVO.getOption_no()[i]);       // 옵션 번호
        VO.setOrder_item_no(orderItemVO.getItem_no()[i]);           // 상품 번호
        VO.setOrder_select_vol(orderItemVO.getSelect_vol()[i]);     
        orderList.add(VO);

      }
      
      model.addAttribute("order", orderList);
      model.addAttribute("totalPrice", totalPrice);
      model.addAttribute("totalVol", totalVol);
      
      if(session.getAttribute("member") != null) {

        MemberVO memberVO = (MemberVO) session.getAttribute("member");
      
        model.addAttribute("coupon", mainService.orderCoupon(memberVO.getMEM_NO()));
      }

      return "/main/orderView";
    }

    /* 
      로직 흐름 = 회원가입 - 로그인 - 상품 장바구니에 추가 - 장바구니 - 주문 - 주문확인
      때문에, 상품을 직접 주문하는 것이 아닌, 일단 장바구니에 담은 후 장바구니에 페이지로 이동해서 수량을 설정하고 주문하는 것이다.
      때문에, 주문페이지의 주문상품들은 따로 select문을 돌리지 않고, 장바구니 페이지에 담긴 상품들의 정보, 옵션, 수량을 그대로 가져오도록 했다.
      장바구니 목록을 select문을 이용해 List 형태로 출력하는데, 거기에 input 태그를 hidden태그를 감싼 submit되어,
      주문버튼을 누르면 input태그를 감싼 form이 submit되어, 해당 값을 이용해서 주문페이지의 주문상품목록을 구성했다.
      장바구니 목록을 다수이기 때문에, 컨트롤러에서 배열로 받아서 for문으로 처리해줬다. 

      사실 장바구니 페이지의 상품PR과 옵션PR로 join을 해서 select문을 이용해 List를 출력할 수도 있겠다.
      하지만 그냥 이런게 간단히 장바구니의 파라미터를 주문페이지로 넘겨도 로직에 이상이 없겠다는 생각이 들어서
      따로 쿼리를 쓰지 않는 방향으로 구현했다.
    */
}
