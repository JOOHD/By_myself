package practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocaleDate;

@Service
public class 국민은행원 implements 은행원인터페이스 {
    @Autowired
    private 입출금관리시스템인터페이스 입출금관리시스템;
    
}
