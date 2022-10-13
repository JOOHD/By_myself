package contorller;

import com.mieumje.springboot.config.auth.LoginUser;
import com.mieumje.springboot.config.auth.dto.SessionUser;
import com.mieumje.springboot.service.PostsService;
import com.mieumje.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("post", postsService.findAllDesc());
        /*  
         * CustomOAuth2UserService에서 로그인 성공 시 
           세션에 SessionUser를 저장하게 된다.
           로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.

         * if(user != null)
           세션에 저장된 값이 있을 때만 model에 UserName으로 등록. 
        */
        // userName을 사용할 수 있게 model에 저장
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    // 글 저장
    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    // 글 수정
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) [
        PostsResponseDto dto = postsService.findByid(id);
        model.addAttribute("post", dto);

        return "posts-update";
    ]
}
