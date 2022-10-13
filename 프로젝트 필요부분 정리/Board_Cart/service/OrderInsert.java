package service;

public class OrderInsert {
    
    @Transactional
		  public void orderInsert(OrderedVO orderedVO, OrderItemInsertVO itemInsertVO)throws Exception{
			  mainDAO.orderInsert(orderedVO);
			  int orderNo = orderedVO.getOrdered_no();
			  
			  
			  itemInsertVO.setOrderitem_order_no(orderNo);
			  itemInsertVO.setOrderitem_mem_no(orderedVO.getOrdered_mem_no());
			  for(int i =0; i < (itemInsertVO.getInorderitem_name()).length; i++) {
				  itemInsertVO.setOrderitem_img(itemInsertVO.getInorderitem_img()[i]);
				  itemInsertVO.setOrderitem_name(itemInsertVO.getInorderitem_name()[i]);
				  itemInsertVO.setOrderitem_option(itemInsertVO.getInorderitem_option()[i]);
				  itemInsertVO.setOrderitem_price(itemInsertVO.getInorderitem_price()[i]);
				  itemInsertVO.setOrderitem_item_no(itemInsertVO.getInorderitem_item_no()[i]);
				  itemInsertVO.setOrderitem_select_vol(itemInsertVO.getInorderitem_select_vol()[i]);
				mainDAO.orderItemInsert(itemInsertVO);
				//상품 옵션 개수 감소
				mainDAO.itemVolDelete(itemInsertVO.getOrderitem_select_vol(), itemInsertVO.getOrderitem_item_no());
			  }
			  
			  
			  //쿠폰 사용시 사용내역 , 쿠폰삭제
			  if(orderedVO.getOrdered_cpn_disc() != 0) {
				  
			  String history_content = "쿠폰 :[ "+orderedVO.getOrdered_cpn_name() + "]을 사용하셨습니다";
			  int history_mem_no = orderedVO.getOrdered_mem_no();
			  mainDAO.historyInsert(history_content, history_mem_no);
			  mainDAO.couponDelete(orderedVO.getOrdered_cpn_name(), orderedVO.getOrdered_mem_no());
			  }
			  
			  //적립금 사용시 사용내역, 적립금 감소
			  if(orderedVO.getOrdered_usepoint() != 0) {
				  String history_content = "적립금  : ["+orderedVO.getOrdered_usepoint() + "원]을 사용하셨습니다";
				  int history_mem_no = orderedVO.getOrdered_mem_no();
				  mainDAO.historyInsert(history_content, history_mem_no);
				  mainDAO.pointDelete(orderedVO.getOrdered_usepoint(), orderedVO.getOrdered_mem_no());
			  }
			  
			  //장바구니 초기화
			  mainDAO.cartReset(orderedVO.getOrdered_mem_no());
			  
	
		  }
}
