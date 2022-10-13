package main.java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vam.mapper.AdminMapper;
import com.vam.mapper.AttachMapper;
import com.vam.mapper.BookMapper;
import com.vam.model.AttachImageVO;
import com.vam.model.BookVO;
import com.vam.model.CateFilterDTO;
import com.vam.model.CateVO;
import com.vam.model.Criteria;
import com.vam.model.SelectDTO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Autowired
    private AdminMapper adminMapper;
    
    /* 상품 검색 */
    @Override
    public List<BookVO> getGoodsList(Criteria cri) {

        log.info("getGoodsList().......");

        // String(타입)의 = cri.([검색 타입].메소드 불러오기);
        String type = cri.getType();
        // String[배열] =  splic(문자열을 분할하는 메서드) (',') [쉼표를 기준으로 분할]
        String[] typeArr = type.split("");
        /* 
          1.작가배열 = 작가id리스트(cri.[검색 대상]);
          2.getGoodsList() Service 메서드에서 Criteria 객체에 authorArr이 부여되면 이 객체를
            그대로 goodsGetTotal()에서 사용하기 때문에 getGoodsList() 메서드에만 새로운 Mapper 메서드를 적용
        */
        String[] authorArr = bookMapper.getAuthorIdList(cri.getKeyword());

        if(type.equals("A") || type.equals("AC") || type.equals("AT") || type.equals("ACT")) {
            
            // 배열이 비어있을 경우
            if(authorArr.length == 0) {

                return new ArrayList();
            }
        }

        // 검색 조건에 '작가'에 대한 검색이 있을 때만 실행
        for(String t : typeArr) {

            if(t.equals("A")) {

                cri.setAuthorArr(authorArr);
            }
        }

        List<BookVO> list = bookMapper.getGoodsList(cri);

        list.forEach(book -> {

            int bookId = book.getBookId();

            List<AttachImageVO> imageList = attachMapper.getAttachList(bookId);

            book.setImageList(imageList);

        });

        return list;
    }

    /* 상품 총 개수 */
    @Override
    public int goodsGetTotal(Criteria cri) {

        lgo.info("goodsGetTotal().......");

        return bookMapper.goodsGetTotal(cri);
    }

    /* 국내 카테고리 리스트 */
    @Override
    public List<CateVO> getCateCode1() {

        log.info("getCateCode1()........");

        return bookMapper.getCateCode1();
    }

       /* 외국 카테고리 리스트 */
    @Override
    public List<CateVO> getCateCode2() {
		
		log.info("getCateCode2().........");
		
		return bookMapper.getCateCode2();
	}	

    /* 검색결과 카테고리 필터 정보 */
    @Override
    public List<CateFilterDTO> getCateInfoList(Criteria cri) {

        List<CateFilterDTO> filterInfoList = new ArrayList<CateFilterDTO>();

        // 타입 리스트 "" 구분 짓기
        String[] typeArr = cri.getType().split("");
        // 작가 리스트 초기화
        String[] authorArr;

        for(String type : typeArr) {

            /*
                Criteria 객체의 type 변수에 "A"가 포함된 경우 getCateList() Mapper 메서드가 수행할 쿼리문에
                authorId의 정보가 필요로 한다.
            */
            if(type.equals("A")) {

                //  검색 Service 메서드인 getAuthorIdList() Mapper 메서드를 호출 authorId 값들을 반환받고,
                authorArr = bookMapper.getAuthorIdList(cri.getKeyword());

                /*
                     type이 "A", "AC"이고 authorArr요소에 authorId가 없는 경우,
                     getCateInfo() Mapper 메서드가 에러가 나기 때문에, authorArr 배열이 요소를 가지지 않는 경우,
                     getCateInfo() 메서드가 실행되지 않도록 해주어야 합니다.

                     따라서 getAuthorIdList() 메서드를 호출한 바로 다음 공간에 authorArr 배열의 길이를 체크해서
                     0인 경우, getCateInfo() 메서드가 실행이 될 필요가 없기 때문에 Service 메서드가 바로 return 이 될 수 있도록 코드를 추가
                 */
                if(authorArr.length == 0) {
                    
                    return filterInfoList;
                }
                // 이를 Criteria 객체에 추가해준다.
                cri.setAuthorArr(authorArr);
            }
        }
        /* 
            필터 정보의 대상이 될 '카테고리 코드'를 반환해주는 getAuthorIdList를 호출하고, 
            반환 값을 String 배열 타입의 cateList 변수에 저장
        */    
        String[] cateList = bookMapper.getCateList(cri);

        /*
            cateList의 배열에 담긴 각각의 '카테고리 코드'들을 Criteria 객체에 담아서 getcateInfo() Mapper 메서드의
            파라미터로 전달 할 예정인데 Criteria 객체의 기존 cateCode 값을 유지할 수 없게 된다. 
            기존의 cateCode를 유지해주기 위해 두 가지 방안이 잇따.
            1.getCateInfo() Mapper 메서드에 전달 파라미터 용도로만 사용해줄 Criteria 타입의 객체를 새로 만들어서 거기에 데이터를 담고 사용.
            2.기존의 '카테고리 코드(cateCode)'를 새로운 변수를 선언하여 임시로 담고 Service 메서드가 끝나기전 임시저장 cateCode를 다시 저장.

            ※ Criteria의 'cateCode' 기존 값을 유지 해 주지 않는다면 필터링된 '상품 화면' 결과 화면에서 페이지 인터페이스를 통해 페이지를 이동했을 때
             전혀 다른 결과물이 나올 수 있기 때문입니다.
         */

        // 임시 저장해 줄 String 타입의 tempCateCode 변수를 선언 Criteria 객체의 cateCode 값을 저장해준다.
        String tempCateCode = cri.getCateCode();

        // cateList 모든 요소들을 순회하여 처리할 수 있도록 향산된 for문 작성
        for(String cateCode : cateList) {

            // cateList 요소에 있는 '카테고리 코드'를 Criteria에 "cateCode" 변수에 저장을 한다.
            cri.setCateCode(cateCode);

            // 호출한 메서드가 반환한 카테고리 정보가 담긴 CateFilterDTO 타입 객체를 
            CateFilterDTO filterInfo = bookMapper.getCateInfo(cri);

            // getCateInfoList() 메서드가 반환 할 List 객체(filterInfoList)에 요소로 추가해준다.
            filterInfoList.add(filterInfo);
        }

        // 임시로 저장해둔 '카테고리 코드'(tempCateCode) 값을 Criteria의 cateCode 값에 저장해준다.
        cri.setCateCode(tempCateCode);

        // 카테고리 코드'가 담긴 List 객체 "filterInfoList"를 return문에 추가해줍니다.
        return filterInfoList;
    }

    /* 상품 정보 */
    @Override
    public BookVO getGoodsInfo(int bookId) {

        BookVO goodsInfo = bookMapper.getGoodsInfo(bookId);

        goodsInfo.setImageList(adminMapper.getAttachInfo(bookId));

        return goodsInfo;
    }

    @Override
    public BookVo getBookIdName(int bookId) {

        return bookMapper.getBookIdName(bookId);
    }

    @Override
    public List<SelectDTO> likeSelect() {

        List<SelectDTO> list = bookMapper.likeSelect();

        list.forEach(Dto -> {

            int bookId = dto.getBookId();

            List<AttachImageVO> imageList = attachMapper.getAttachList(bookId);

            dto.setImageList(imageList);

        });

        return list;
    }


}
