package model;

public class OrderItemInsertVO {

    // 주문상품이 들어갈 VO

    // 주문테이블에 주문상품 컬럼을 만들어서 [,]기준으로 배열로 저장되게 하여 
    // 상품의 PR을 넣어서 select하여 구성할 수 있지만, 시간적 여유가 없어서 join을 남발하지 못 할 상황 때문에
    // 통으로 정보를 insert한다.
	
	private int[] Inorderitem_no; //주문아이템 pr
	private String[] Inorderitem_img; 
	private String[] Inorderitem_name;
	private String[] Inorderitem_option;
	private String[] Inorderitem_price;

	private int[] Inorderitem_item_no;
	private int[] Inorderitem_select_vol;

    
	private int orderitem_no; //주문아이템 pr
	private int orderitem_order_no; // 주문번호
	private int orderitem_mem_no;
	private String orderitem_img; 
	private String orderitem_name;
	private String orderitem_option;
	private String orderitem_price;
	
    private int orderitem_item_no;
	private int orderitem_select_vol;
    
    //getter&setter
    
    }
