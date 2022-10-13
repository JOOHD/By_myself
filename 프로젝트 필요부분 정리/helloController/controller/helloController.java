package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class helloController {

    @GetMapping("hello")
    public String(Model model){
        model.Attribute("data", "spring");
        return "hello";
    }
}

/** 
-@GetMapping("hello") : get요청이 hello로 들어오면 hello메소드를 호출하여서 hello.html 파일을 리턴하게하는 어노테이션이다.
-hello.html 파일의 경우 thymLeaf를 사용해서 웹 페이지를 띄우고 있다.
-$data는 model의 data로 넘어가는 key값이고, "spring!"이라는 value를 웹페이지에서 리턴한다.
*/

/**
 * Controller에서 리턴 값으로 문자를 반환하면, view-resolver 가 해당 문자열의 이름을 갖는 html 파일을 찾아서 서버를 거쳐 브라우저에 띄운다.
 * 스프링에서 view-resolver가 html을 찾는 기본 경로는 resource/templates이다.
 * 그림을 보면 스피링 부트는 내장 톰캣 서버를 가지고 있기에, 따로 WAS가 필요하지 않고, JAR파일로 배포가 가능핟.
 */