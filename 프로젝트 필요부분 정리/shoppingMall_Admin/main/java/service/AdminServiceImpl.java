package main.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vam.mapper.AdminMapper;
import com.vam.model.AttachImageVO;
import com.vam.model.BookVO;
import com.vam.model.CateVO;
import com.vam.model.Criteria;
import com.vam.model.OrderDTO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AdminServiceImpl {
    
    @Autowired
    private AdminMapper adminMapper;

    /* 상품 등록 */
    @Transactional
    @Override
    public void bookEnroll(BookVO book) {

        log.info("(service)bookEnroll........");

        adminMapper.bookEnroll(book);

        if(book.getImageList() == null || book.getImageList().size() <= 0) {

            return;
        }

            book.getImageList().forEach(attach ->{

            attach.setBookId(book.getBookId());
            adminMapper.imageEnroll(attach);

        });


    }

    /* 카테고리 리스트 */
    @Override
    public List<CateVO> cateList() {
    
        log.info("(service)cateList.......");

        return adminMapper.cateList();
    }

    /* 상품 리스트 */
    @Override
    public List<BookVO> goodGetList(Criteria cri) {

        log.info("goodsGetTotalList()........");

        return adminMapper.goodsGetList(cri);
    }

    /* 상품 조회 페이지 */
    @Override
    public BookVO goodsGetDetail(int bookId) {

        log.info("(service)bookGetDetail......." + bookId);

        return adminMapper.goodsGetDetail(bookId);
    }

    /* 상품 정보 수정 */
    @Transactional // DB 작업 시, INSERT, UPDATE, DELETE등으로 이루어진 작업 중, 오류가 발생했을 때 모든 작업을 원상태로 복구
    @Override
    public int goodsModify(BookVO vo) {

        // 먼저 수정된 상품 정보를 DB에 반영하는 메서드를 호출, 수정 결과를 result 변수를 선언하여 대입, 그 결과 return
        int result = adminMapper.goodsModify(vo);

        /* if 문
          1. 상품 수정이 정상적으로 되었을때(result == 1)   
          2. 뷰로부터 상품 이미지 정보가 없는 경우는 상품 이미지 정보 수정 코드가 실행될 필요없기에 이미지 정보 존재 여부 확인 
         */
        if(result == 1 && vo.getImageList() != null && vo.getImageList().size() > 0) {

            // 이미지 정보 모두 삭제되도록 deleteImageAll() 메서드 호출
            adminMapper.deleteImageAll(vo.getBookId());

            /* List 문
               List 자료 형태로 전달받은 이미지 정보(BookVO의 ImageList)를 각 요소 순서대로 이미지 정보를
               DB에 저장되도록 코드를 작성
               (BookVO에 있는 imageList의 각 요소로 있는 AttachImageVO객체에 있는 bookId 변수에는 값이 할당이 되지 않음, 해당 값을 세팅)
             */
            vo.getImageList().forEach(attach -> {
                
                attach.setBookId(vo.getBookId());
                adminMapper.imageEnroll(attach);
            
            });

        }
        return result;
    }

    /* 상품 정보 삭제 */
    @Override
    public int goodsDelete(int bookId) {

        log.info("goodsDelete.......");

        adminMapper.deleteImageAll(bookId);

        return adminMapper.goodsDelete(bookId);
    }

    /* 지정 상품 이미지 정보 얻기 */
    @Override
    public List<AttachImageVO> getAttachInfo(int bookId) {

        log.info("getAttachInfo.......");

        return adminMapper.getAttachInfo(bookId);
    }

    /* 주문 상품 리스트 */
	@Override
	public List<OrderDTO> getOrderList(Criteria cri) {

		return adminMapper.getOrderList(cri);
	}
	
	/* 주문 총 갯수 */
	@Override
	public int getOrderTotal(Criteria cri) {

		return adminMapper.getOrderTotal(cri);
	}

}
