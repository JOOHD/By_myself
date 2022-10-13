import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
    
    @Autowired
    private ProductService ProductService;

    @RequestMapping(value = "admin_enroll_product", method = RequestMethod.POST) 
    public String enrollProductAction(
            @RequestParam(value = "product_image",, defaultValue = "", required = false) 
                MultipartFile uploadFile,
                MultipartHttpServletRequest mtRequest,
                Model model, 
                HttpSession session,
                ProductVO vo) {

                    AdimnVO employee = (AdminVO) session.getAttrinute("adminUser");

                    if(employee == null) {
                        return "admin/login";
                    } else {

                        List<MultipartFile> detailImgFileList = mtfRequest.getFiles("product_detail_image");

                        int prodnum = productService.maxproductNum();

                        vo.setProdnum(prodnum);

                        String thumnailFileName  = "";
                        
                        if(!uploadFile.isEmpty()) {

                            String root_path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");

                            thumnailFileName = uploadFile.getOriginalFilename();

                            File file = new File(root_path + thumnailFileName);

                            try {
                                uploadFile.transferTo(file);
                            } catch (IllegalStateException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        vo.setImage(thumnailFileName);
                        vo.setPrice3(vo.getPrice2() - vo.getPrice1());

                        productService.enrollProduct(vo);

                        if(detailImgFileList.size() < 1) {

                            vo.setDetail_image("");

                            productService.insertProductImage(vo);

                        } else {

                            for(MultipartFile mf : detailImgFileList) {

                                String fileName = mf.getOriginalFilename();

                                String detail_img_root_path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");

                                File detailFile = new File(detail_img_root_path + fileName);
                                
                                try {
                                    mf.transferTo(detailFile);
                                } catch (IllegalStateException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                
                                vo.setDetail_image(fileName);
                                productService.insertProductImage(vo);
                            }
                        }
                        return "redirect:admin_product_list";
                    }
                }
}
