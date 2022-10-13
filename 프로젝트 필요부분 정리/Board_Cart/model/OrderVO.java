package model;

public class OrderedVO {

    // 주문 내역

	private int ordered_no; //PR
	private int ordered_mem_no; // 주문회원PR
	private String ordered_to_name; //주문자
	private String ordered_to_tel;
	private String ordered_to_email;
	private String ordered_to_adr;
	private String ordered_to_post;
	private String ordered_from_name; //수취인(받는사람)
	private String ordered_from_tel;
	private String ordered_from_adr;
	private String ordered_from_post;
	private int ordered_cartprice; //총액
	private int ordered_orderprice; //결제금액
	private String ordered_cpn_name; //쿠폰명
	private int ordered_cpn_disc; //쿠폰할인액
	private int ordered_usepoint; // 적립금사용액
	private int ordered_payhow; //결제방식
	private int ordered_paystate; //결제상태 1.일반 2.취소 3. 4.교환
	private int ordered_delivstate; //배송상태 1.물품준비중 , 2.배송전, 3.배송완료
	private String ordered_delivnum; //송장번호
	private Date ordered_date;  //주문일
	private Date ordered_cdate; //주문취소일
    
    //  getter&setter
    }
