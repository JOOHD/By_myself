import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class MediaType {
    
    @Controller
    public class SampleController {

        @GetMapping(value = "/hello", consumes = MediaType.APPLICATION_JSON_VALUE) // consumes
        @ResponseBody
        public String hello() {
            return "hello";
        }
    }
}
/*
 *  consumes & produces
 *      consumes : 소비 가능한 미디어 타입의 목록을 지정해서 주요한 매핑을 제한함. 
 *      content-Type 요청 헤더가 consumes에 지정한 미디어 타입과 일치할 때만 요청을 매핑해줌
 *     
 *      produces : 생산 가능한 미디어 타입의 목록을 지정해서 주요 매핑을 제한할 수 있다.
 *      Accept 요청헤더가 이러한 값 중 하나와 일치할 때만 요청이 매칭 된다.
 * 
 *  즉, consumes는 브라우저가 서버에게 보낼때 거르는 것이고, 
 *  produces는 반대로 서버에서 브라우저에게 보낼 때 거르는 것이다. 
 */

@RestController
@RequestMapping(value = "/ex1", method = "RequestMethod.GET", consumes = "applicatioin/json")
public String ex1() { // $ curl -i -H "Content-Type:application/json" http://localhost:8080/ex1 
    return "ex1"; // Content-Type은 명시한 타입으로 보내겠다는 말이 된다.
}

@RestController
@RequestMapping(value = "/ex1", method = "RequestMethod.GET", produces="application/json")
public String ex1() { // $ curl -i -H "Accept: application/json" http://localhost:8080/ex1
	return "ex1"; // Accept는 명시한 타입으로 응답받겠다는 말이 된다.
}

// TEST CODE
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// ​
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testURI() throws Exception {

        mockMvc.perform(get("/hello")
               .contentType(MediaType.APPLICATION_JSON_UTF8)) // 이부분
               // MediaType을 APPLICATION_JSON_UTF8로 보냈고,  
               // controller 코드에서는 MediaTYpe.APPLICATION_JSON_VALUE 이렇게 VALUE로 받는걸 볼 수 있다.
               // content-Type은 http의 스펙이지만, charset은 스펙이 아니고 특정 브라우저에서 지원해주는 기능이다. 따라서 UTF-8로 해도 에러 안남
               .andDo(print())
               .andExpect(status().isOK());
    }
}

/* 
MockHttpServletRequest:
  HTTP Method = GET
  Request URI = /hello
   Parameters = {}
		  Headers = [Content-Type:"application/json;charset=UTF-8"]
				 Body = null
Session Attrs = {}
​
Handler:
	 Type = com.hwan.jpaTest.controller.SampleController
   Method = com.hwan.jpaTest.controller.SampleController#hello()
​
Async:
	Async started = false
	 Async result = null
​
Resolved Exception:
			 Type = null
​
ModelAndView:
	View name = null
			 View = null
			Model = null
​
FlashMap:
   Attributes = null
​
MockHttpServletResponse:
		   Status = 200
Error message = null
		  Headers = [Content-Type:"text/plain;charset=UTF-8", Content-Length:"5"]
 Content type = text/plain;charset=UTF-8
				 Body = hello
Forwarded URL = null
Redirected URL = null
		  Cookies = []
*/
