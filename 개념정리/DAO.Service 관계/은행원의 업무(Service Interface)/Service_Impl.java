package net.kurien.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocaleDate;

@Service
public class 국민은행원 implements 은행원인터페이스 {
    @Autowired
    private 입출금관리시스템인터페이스 입출금관리시스템;

    @Override
    public int 입금(입출금전표 전표, int 입금할돈) {
        if(전표 == null){
            throw new 전표미입력Exception("저기 있는 입출금전표 작성해오세요.");
        }

        if(전표.get계좌() == null || 전표.get계좌().equals("") || 전표.get금액() <= 0) {
            throw new 전표오작성Exception("여기 여기 잘못 적으셨어요. 다시 적어주세요.");
        }

        if(전표.get금액() > 입금할돈) {
            throw new 입금할돈부족Exception("입금할 돈이" + 전표.get금액() - 입금할돈 + "원 부족한데요?");
        }

        // 이 부분은 은행원이 입력할 내용
        String 입금지점 = "국민은행한국지점";
        LocalDate 입금시간 = LocalDate.now();

        입출금관리시스템입력양식 입력양식 = new 입출금관리시스템입력양식(전표.get계좌(), 전표.get금액(), 입금시간, 입금지점);

        try {
            입출금관리시스템.입금(입력양식);
        } catch(은행전산마비Exception e) {
            throw new 은행원당황Exception("죄송한데 은행 전산이 마비돼서요;; 지금은 입금이 안될 것 같습니다.")
        }
        
        return 입금할돈 - 전표.get금액();
    }    
}