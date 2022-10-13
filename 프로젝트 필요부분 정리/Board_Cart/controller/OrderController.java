package controller;

public class OrderController {
    
    @RequestMapping(value="/orderInsert", method=RequestMethod.POST)
    public String orderInsert(OrderedVO orderedVO, OrderItemInsertVO itemInsertVO)throws Exception{

        logger.info("orderVOtest="+merchant_uid);

        String ckid = RandomStringUtils.randomNumeric(8);

        int orderedNo = Integer.parseInt(ckid);
        
        orderedVO.setOrdered_no(orderedNo);
        mainService.orderInsert(orderedVO, itemInsertVO);
        
        return "redirect:/main/cartView";
    }
}
