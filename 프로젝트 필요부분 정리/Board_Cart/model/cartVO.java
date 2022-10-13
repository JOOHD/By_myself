package model;

public class CartVO {

   private int cart_no; //장바구니PR
   private int cart_mem_no; //회원PR
   private int cart_item_no; //상품PR
   private Date cart_cklimit; //쿠키제한시간(삭제용)
   private String cart_ckid; //쿠키value값
   private String cart_option_content; //옵션내용
   private int cart_option_no; //옵션PR
   private ItemVO itemVO; // join용
   private OptionVO optionVO; // join용
    
    // 테이블은 위와 동일하며, 생성시간을 기록할 cart_date가 default 값 sysdate로 들어가있다.
    
    //getter & setter
}
