package net.kurien.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class kurienController {

    @Autowired
    private 은행원인터페이스 은행원;

    public String 저축 (Model model) {
        String 계좌번호 = "0000-1111-2222";
        int 내돈 = 10000;

        입출금전표 전표 = new 입출금전표(계좌번호, 5000);

        try {
            int 거스름톤 = 은행원.입금(전표, 내돈);

            내돈 = 거스름돈;

            return "success";
        } catch(은행강도Exception e) {
            // 도망친다.
            return "run";
        } catch(Exception e) { // 예상하지 못한 오류
            return "그때 가서 생각한다";
        }
    }
}