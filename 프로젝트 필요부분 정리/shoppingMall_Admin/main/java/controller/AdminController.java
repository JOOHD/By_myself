package main.java.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vam.model.AttachImageVO;
import com.vam.model.AuthorVO;
import com.vam.model.BookVO;
import com.vam.model.Criteria;
import com.vam.model.OrderCancelDTO;
import com.vam.model.OrderDTO;
import com.vam.model.PageDTO;
import com.vam.service.AdminService;
import com.vam.service.AuthorService;
import com.vam.service.OrderService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderService OrderService;

    /* 관리자 메인 페이지 이동 */
                        // 페이지 이동은 GET, INSERT, UPDATE, DELETE, CREATE는 POST
    @RequestMapping(value="main", method=RequestMethod.GET)
    public void adminMainGET() throws Exception {

        log.info("관리자 페이지 이동");

    }

    /* 상품 관리(상품목록) 페이지 접속 */
    @RequestMapping(value="goodsManage", method=RequestMethod.GET)
                        //  페이지 이동이아닌, 접속에는 model 객체 사용
    public void goodsManage(Criteria cri, Model model) throws Exception {

        log.info("상품 관리(상품목록) 페이지 접속");

        /* 상품 리스트 데이터 */
        List list = adminService.goodsGetList(cri);

        if(!list.isEmpty()) {

            model.addAttribute("list", list);

        } else {

            model.addAttribute("listCheck", "empty");

            // 이건 리턴타입이 void인데, return을 쓰는 이유는 로직에서 벗어나고자 하는 return문이다.
            return;
        }

        /* 페이지 인터페이스 데이터 */
        /* 
          PageMakerDTO 클래스의 데이터를 VIEW로 보내기 위해 addAttribute()메소드를 활용하여 "pageMaker" 속성에 저장
          게시판 리스트는 원하는 페이지의 데이터를 읽어 올 수 있도록 페이징 정보(Pagination 객체)를 파라미터로 보낼 수 있게 수정을 합니다. 
          따라서 BoardService 와 BoardDAO의 getBoardList() 메소드가 Pagination 객체를 사용 할 수 있도록 모두 수정을 해야 합니다.

         */
        model.addAttribute("pageMaker", new PageDTO(cri, adminService.goodsGetTotal(cri)));
    }

    /* 상품 등록 페이지 접속 */
    @RequestMapping(value="goodsEnroll", method=RequestMethod.GET)
    public void goodsEnrollGET(Model model) throws Exception {

        log.info("상품 등록 페이지 접속");

        // static 메서드가 아니여서, 인스턴스화 시키고, ObjectMapper 타입의 "mapper" 변수를 선언 후, ObjectMapper 객체로 초기화
        ObjectMapper objm = new ObjectMapper();

        // 상품 카테고리 리스트 메소드 호출
        List list = adminService.cateList();

        // writeValueAsString = Java 객체를 String 타입의 JSON형식 데이터로 변환
        String cateList = objm.writeValueAsString(list);

        model.addAttribute("cateList", cateList);
    }

    /* 상품 조회 페이지, 상품 수정 페이지 이동*/
    @GetMapping({"/goodsDetail", "/goodsModify"})
    public void goodsGetInfoGET(int bookId, Criteria cri, Model model) throws JsonProcessingException {

        log.info("goodsGetInfo()......." + bookId);

        ObjectMapper objm = new ObjectMapper();

        /* 카테고리 리스트 데이터 */
        // objm.writeValueAsString = Java 객체를 String 타입의 JSON형식 데이터로 변환
        model.addAttribute("cateList", objm.writeValueAsString(adminService.cateList()));

        /* 목록 페이지 조건 정보 */
        model.addAttribute("cri", cri);

        /* 조회 페이지 정보 */
        model.addAttribute("goodsInfo", adminService.goodsGetDetail(bookId));
    }

    /* 상품 정보 수정 */
    @PostMapping("/goodsModify")
    public String goodsModifyPOST(BookVO vo, RedirectAttributes rttr) {

        log.info("goodsModifyPOST......." + vo);

        int result = adminService.goodsModify(vo);

        rttr.addFlashAttribute("modify_result", result);

        // 상품 정보 수정이 완료되면, 상품관리 페이지로 redirect 
        return "redirect:/admin/goodsManage";
    }

    /* 상품 정보 삭제 */
    @PostMapping("/goodsDelete")
    public String goodsDeletePOST(int bookId, RedirectAttribute rttr, AttachImageVO vo) {

        log.info("goodsDeletePOST......");

        // 먼저 이미지 정보를 반환해주는 service 메소드를 호출하고 반환받은 값을 List 타입의 fileList 변수에 저장해준다.
        List<AttachImageVO> fileList = adminService.getAttachInfo(bookId);

        /*
          변수 fileList에는 상품에 대한 이미지가 존재한다면 AttachImageVO 객체를 요소로 가지는 List 객체가 저장되어 있을 것,
          이미지가 존재하지 않는다면 null일 것이다. 이미지가 없다면 굳이 서버 파일 삭제 코드들이 실행이 될 필요가 없기 때문에,
          이미지가 존재 시 코드가 실행이 될 수 있도록 if문을 작성해 준다.
        */
        if(fileList != null) {

            // 파일 존재 시, DB로부터 가져온 이미지 정보를 활용하여 Path 객체를 생성
            List<Path> pathList = new ArrayList();;

            fileList.forEach(vo ->{
    
                // 원본 이미지
                Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
                pathList.add(path);
                
                // 섬네일 이미지
                Path path = Paths.get("C:\\upload", vo.getUploadPath(), "s_" + vo.getUuid()+"_" + vo.getFileName());
                pathList.add(path);

                
            });

            pathList.forEach(path ->{

                    // 1. 서버 저장 이미지 삭제
                    path.toFile().delete();
                    
            });
        } // if

        /*
          이번엔 DB의 데이터를 삭제하는 코드를 추가해줄 차례이다. 기존 코드를 보면 '상품 정보 삭제'를 
          수행하는 Service 메서드가 있는데 해당 메서드에서 '이미지 정보 삭제'에 대한 작업도 수행하도록 만들어 줄 것이다.
          그렇다면 '이미지 정보 삭제'를 수행하는 Mapper 메서드가 필요로한데 이는 앞서 deleteImageAll() 메서드 사용
        */

        int result = adminService.goodsDelete(bookId);

        rttr.addFlashAttribute("delete_result", result);

        return "redirect:/admin/goodsManage";
    }

    /* 작가 등록 페이지 접속 */
    @RequestMapping(value="authorEnroll", method=RequestMethod.GET)
    public void authorEnrollGET() throws Exception {

        log.info("작가 등록 페이지 접속");

    }

    /* 작가 관리 페이지 접속 */
    @RequestMapping(value="authorManage", method=RequestMethod.GET)
    public void authorManageGET(Criteria cri, Model model) throws Exception {

        log.info("작가 관리 페이지 접속......." + cri);

        /* 작가 목록 출력 데이터 */
        List list = authorService.authorGetList(cri);

        if(!list.isEmpty()) {

            model.addAttribute("list", list); // 작가 존재 경우

        } else {

            model.addAttribute("listCheck", "empty"); // 작가 존재하지 않을 경우

        }

        /* 페이지 이동 인터페이스 데이터 */
        // 인터페이스 창 띄워지는 로직 구현
        model.addAttribute("pageMaker", new PageDTO(cri, authorService.authorGetTotal(cri)));
    }

    /* 작가 등록 */
    @RequestMapping(value="authorEnroll.do", method=RequestMethod.POST)
    public String authorEnrollPOST(AuthorVO author, RedirectAttributes rttr) throws Exception {

        // 작가 등록 로그확인
        logger.info("authorEnroll : " + author);

        // 작가 등록 쿼리 실행
        authorService.authorEnroll(author); 

        // 등록 성공 메시지(작가이름)
        // getAuthorName()은 AuthorVO의 authorName에서 가져온다.(get)
        rttr.addFlashAttribute("enroll_result", author.getAuthorName()); 

        // 작가 등록 성공 후, redirect
        return "redirect:/admin/authorManage";

    }

    /* 작가 상세, 수정 페이지 이동 */
    @GetMapping({"/authorDetaiil", "/authorModify"})
    public void authorGetInfoGET(int authorId, Criteria cri, Model model) throws Exception {

        logger.info("authorDetail......" + authorId);

        /* 작가 관리 페이지 정보 */
        model.addAttribute("cri", cri);

        /* 선택 작가 정보 */
        model.addAttribute("authorInfo", authorService.authorGetDetail(authorId));

    }

    /* 작가 정보 삭제 */
    PostMappinig("/authorDelete")
    public String auhtorDeletePOST(nt authorId, RedirectAttiributes rttr) {

        log.info("authorDeletePOST.......");

        int result = 0;

        try {

            //  result 변수에 작가 정보 삭제를 담는다.
            result = authorService.authorDelete(authorId);

        } catch (Exception e) {

        /*
            우리가 신경 써줘야 할 부분이 있따. 우리가 삭제하고자 하는 데이터는 작가 테이블
            (vam_author)의 데이터이다. 문제는 외래 키 조건으로 인해 작가 테이블을 참조(reference)하고 있는
            상품 테이블(vam_book)이 있다는 점이다. 참조되지 않고 있는 행을 지운다면 문제가 없지만 만약 참조되고
            있는 행을 지우려고 시도를 한다면 '무결성 제약조건을 위반'한다는 경고와 함께 예외가 발생한다.

            따라서 try catch문을 사용하여 참조되지 않는 행을 지울땐 삭제를 수행하고 '작가 목록' 페이지로 1을 전송하도록 하고,
            예외가 발생한 상황에서는 '작가 목록 페이지'로 2를 전송하도록 작성

            authorManage.jsp
            /* 삭제 결과 경고창 
            let delete_result = ${delete_result};

            if(delete_result == 1) {
                    alert("삭제 완료");
            } else if(delete_result ==  2) {
                    alert("해당 작가 데이터를 사용하고 있는 데이터가 있어서 삭제 할 수 없다.")
            }
        */    
            e.printStackTrace();

            result = 2;

            rttr.addFlashAttribute("delete_result",, result);

            return "redirect:/admin/authorManager";
        }
    }

    /* 상품 등록 */
    @PostMapping("/goodsEnroll")
    public String goodsEnrollPOST(BookVO book, RedirectAttributes rttr) {
        
        logger.info("goodsEnrollPOST......" + book);
        
        adminService.bookEnroll(book);
        
        rttr.addFlashAttribute("enroll_result", book.getBookName());
        
        return "redirect:/admin/goodsManage";
    }	

    /* 작가 검색 팝업창 */
    @GetMapping("/authorPop")
    public void authorPopGET(Criteria cri, Model model) throws Exception {

        log,info("authorPopGET......");

        cri.setAmount(5);

        /* 게시물 출력 데이터 */
        List list = authorService.authorGetList(cri);

        if(!list.isEmpty()) {

            model.addAttribte("list", list); // 작가 존재 유무
        } else {

            model.addAttribte("listCheck",, "empty"); // 작가 존재하지 않을 경우
        }

        /* 페이지 이동 인터페이스 데이터 */
        // 인터페이스 창 = PopUp 창
        model.addAttribte("pageMaker", new PageDTO(cri, authorService.authorGetTotal(cri)));
    }

    /* 첨부 파일 업로드 */
    // produce 속성을 추가, 전송되는 JSON데이터가 UTF-8인코딩 되어야 한다. 
    // produce 속성은 서버에서 뷰로 전송되는 Response의 Content-type을 제어 할 수 있는 속성이다.
    @PostMapping(value="/uploadAjaxAction", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    // View에서 전송한 첨부파일 데이터를 전달받기 위해서 MultipartFile 타입의 "uploadFile"변수를 매개변수로 부여한다.
    // ResponseEntity<List<AttachImageVO>> = 반환 타입이 ResponseEntity 객체이고 Http의 Body에 추가될 데이터는 List <AttachImageVO>라는 의미이다.
    public ResponseEnity<List<AttachImageVO>> uploadAjaxActionPOST(MultipartFile[] uploadFile) {

        logger.info("uploadAjaxActionPOST........");

        logger.info("파일 이름 : " + uploadFile.getOriginalFilename());
		logger.info("파일 타입 : " + uploadFile.getContentType());
		logger.info("파일 크기 : " + uploadFile.getSize());

        /* 이미지 파일 체크 */
        // for(int i = 0, i < uploadFile.length; i++)
        for(MultipartFile MultiparFile : uploadFile) {

            // 먼저 전달받은 파일(uploadFile)을 File 객체로 만들어주고 File 참조 변수에 대입한다.
            File checkFile = new File(multipartFile.getOriginalFilename());

            // MIME TYPE(이미지 타입 or 다른 것)을 저장할 String 타입의 type 변수를 선언하고 null로 초기화한다.
            String type = null;

            try {

                // 파라미터로 전달받은 파일의 MIME TYPE을 문자열로 설정 위해, checkfile을 path객체로 만들어 주고, File 클래스의 toPath() 메서드 사용
                type = Files.probeContentType(checkFile.toPath());

                // 전달 받은 파일이 이미지 인지 아닌지 체크를 하기 위해서 파일의 MIME TYPE 속성을 활용 쉽게 말해 label이라고 생각하면 된다.
                log.info("MIME TYPE : " + type);

            } catch (IOException e) {

                e.printStackTrace();
            }

            // startsWith() 메서드 선언시 첫 문자가 image가 아닐경우
            if(!type.startsWith("images")) {

                // 전달 해줄 파일의 정보는 없지만 반환 타입이 ResponseEntity<List<AttachImageVO>>이기 때문에 ResponseEntity 객체에 첨부해줄 값 null인 List<AttachImageVO> 타입의 참조 변수 선언
                List<AttachImageVO> list = null;

                // 상태코드가 400인 ResponseEntity 객체를 인스턴스화 하여 이를 반환해주는 코드
                return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);

                /* 
                    View : error400
                    $.ajax({
                        url: '/admin/uploadAjaxAction',
                        processData : false,
                        contentType : false,
                        data : formData,
                        type : 'POST',
                        dataType : 'json',
                        success : function(result) {
                            console.log(result);
                        },
                        error : function(result) {
                            // 콜백함수에 잘못된 파일 선택시, 알리는 문구
                            alert("이미지 파일이 아닙니다.");
                        }
                    });
                 */
            }
        } // for

        /* (uploadAjaxActionPOST)에 파일을 저장할 기본적 경로 초기화 */
        String uploadFolder = "C:\\upload";

        /* 날짜 폴더 경로 */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        /* 오늘의 날짜 데이터를 얻기 위해서 Date 클래스 타입의 변수를 선언 및 초기화 */
        Date date = new Date();

        /* String 타입으로 변환된 값을 String 타입의 str 변수를 선언 */
        String str = sdf.format(date);

        /* 편리하게도 File 클래스에서 실행되는 환경에 따라 
        그에 맞는 경로 구분자를 반환하는 정적(static) 변수인 separator가 있다. */
        String datePath = str.replace("-", File.separator);

        /* 폴더 생성 */
        File uploadPath = new File(uploadFolder, datePath);

            // 이미 존재하는 폴더 재생성 방지(팡리 존재 하면 생성x)
            if(uploadPath.exists() == false) {

                // 폴더를 생성을 수행하기 위해서 File 클래스의 mkdir() 혹은 mkdirs()를 사용할 수 있습니다.     
                uploadPath.mkdirs();
            }

            /* 이미지 정보 담는 객체 */
            List<AttachImageVO> list = new ArrayList();

            /* 향상된 for */
            // multipartFile 이라는 변수에 uploadFile에 있는 정보들을 담는다.
            for(MultipartFile multipartFile : uploadFile) {

                /* 이미지 정보 객체 */
                AttachImageVO vo = new AttachImageVO();

                /* 파일 이름 */
                // File 객체를 만들어 주기 전 먼저 파일의 이름을 사용하기 위해, 아래와 같이 String 타입의 uploadFileName 변수를 선언하여 파일 이름을 저장
                String uploadFileName = multipartFile.getOriginalFilename();
                vo.setFileName(uploadFileName);
                vo.setUploadPath(datePath);

                /* uuid 적용 파일 이름 */
                String uuid = UUID.randomUUID().toString();
                vo.setUuid(uuid);

                uploadFileName = uuid + "_" + uploadFileName;

                /* 파일 위치, 파일 이름을 합친 File 객체 */
                // 파일 저장 위치인 uploadPath와 파일 이름인 uploadFileName을 활용하여 아래와 같이 File 타입의 saveFile 변수를 선언하고 파일 경로와 파일 이름을 포함하는 File 객체로 초기화해준다.
                File saveFile = new File(uploadPath, uploadFileName);

                /* 파일 저장 */
                try {

                    // 파일을 저장하는 메서드인 transferTo()를 호출한다.
                    multipartFile.transferTo(saveFile);

                    /* 썸네일 생성(ImageIO) */
                    /*
                    File thumbnailFile = new File(uploadPath, "s_" + uploadFileName); 
                    
                    BufferedImage bo_image = ImageIO.read(saveFile);
                        //비율 
                        double ratio = 3;
                        //넓이 높이
                        int width = (int) (bo_image.getWidth() / ratio);
                        int height = (int) (bo_image.getHeight() / ratio);				
                    
                    BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                                    
                    Graphics2D graphic = bt_image.createGraphics();
                    
                    graphic.drawImage(bo_image, 0, 0,width,height, null);
                        
                    ImageIO.write(bt_image, "jpg", thumbnailFile);				
                    */

                    /* 이미지 생성 및 저장(Thumbnailator 라이브러리 사용) */
                    File thumbnailFile = new File(uploadPath, "S_" + uploadFileName);

                        // bo_image (buffered original image)
                        BufferedImage bo_image = ImageIO.read(saveFile);

                        // 비율
                        double ratio = 3;

                        // 넓이 높이
                        int width = (int) (bo_image.getWidth() / ratio);
                        int height = (int) (bo_image.getHeight() / ratio);


                    Thumbnails.of(saveFile)
                    .size(width, height)
                    .toFile(thumbnailFile);

                } catch (Exception e) {

                    e.printStackTrace();

                } 

                list.add(vo);

            } // for

            // 최하단에 아래와 같이 ResponseEntity 참조 변수를 선언하고 생성자로 초기화 합니다.
            ResponseEnity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVo>>(list, HttpStatus.OK);

            return result;
        }

        /* 이미지 파일 삭제 */
        // 이미지 파일 삭제는 관리자만 수행할 수 있게 하기위해 adimnController
        @PostMapping("/deleteFile")
        public ResponseEntity<String> deleteFile(String fileName) { 

            /*
               파일 경로 및 이름 전달받기 위해 String 타입의 fileName 변수 파라미터 부여             
               반환 타입 ResponseEntity, HTTP Body에 String 데이터를 추가하기 위해, 타입 매개변수 String 부여
             */
            log.info("deleteFile........" + fileName);

            // url 매칭 메서드의 구현 부에 File 타입의 참조 변수를 선언, null로 초기화
            File file = null;

            try {
                /* 썸네일 파일 삭제 */
                // (file = 풀경로 파일) 삭제할 파일을 대상으로 File 클래스를 인스턴스화 하여 앞서 선언한 file 참조 변수가 참조하도록 한다.
                /* 
                  URLDecoder.decode = 바이트(컴퓨터 언어) 문자를 -> String으로 변환(사람이 읽을 수 있는 언어)
                     System.out.println(URLEncoder.encode(txt, "UTF-8")); //%EC%95%84%EB%B6%80%ED%83%B1
                     System.out.println(URLDecoder.decode(txtEnc, "UTF-8")); //아부탱
                */
                file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
                
                file.delete();
                
                /* 원본 파일 삭제 */
                // getAbsolutePath() : 호출하면 대상 File 객체의 경로를 문자열(String) 타입의 데이터로 반환 해준다.
                String originFileName = file.getAbsolutePath().replace("s_", "");
                
                logger.info("originFileName : " + originFileName);
                
                file = new File(originFileName);
                
                file.delete();
                
                
            } catch(Exception e) {
                
                    e.printStackTrace();
                
                    // 실패 유무를 알리기 위해 return문을 catch 구현부에 작성
                    return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
                
            } // catch

            // 성공 유무를 알리기 위해 return문 뷰로 전송
            return new ResponseEntity<String>("success", HttpStatus.OK);
        }

        /* 주문 현황 페이지 */
        @GetMapping("/orderList")
        public String orderListGET(Criteria cri, Model model) {

            List<OrderDTO> list = adminService.getOrderList(cri);

            if(!list.isEmpty()) { // 리스트가 비어있지 않다면

                model.addAttribte("list", list);
                // 주문페이지 전체목록 불러오기
                model.addAttribute("pageMaker", new PageDTO(cri, adminService.getOrderTotal(cri)));
            } else { // 리스트가 비어있다면

                model.addAttribte("listCheck", "empty");
            }

            return "/admin/orderList";
        }

        /* 주문삭제 */
        @PostMapping("/orderCancle")
        public String orderCanclePOST(OrderCancelDTO dto) {

            orderService.orderCancle(dto);

            return "redirect:/admin/orderList?keyword=" + dto.getKeyword() + "&amouont=" + dto.getAmount() + "&pageNum=" + dto.getPageNum();
        }
}
