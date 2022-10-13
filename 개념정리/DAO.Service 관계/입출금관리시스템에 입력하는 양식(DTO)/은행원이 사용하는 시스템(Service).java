package net.kurien.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class 국민은행입출금관리시스템 implements 입출금관리시스템인터페이스 {
    @Autowired
    private 입출금관리DAO인터페이스 입출금관리 DAO;

    @Autowired
    private 입출금안내서비스인터페이스 입출금안내서비스;

    @Override
    public boolean 입금(입출금관리시스템입력양식 입력양식) {
        try {
            입출금관리DAO.insert(입력양식);
        } catch(SQLException e) {
            throw 은행전산마비Exception("DB가 펑하고 터져서 전산마비");
        }
        try {
            // 입출금안내서비스 클래스는 추가 작성하기가 귀찮아서 생략
            입출금안내서비스.입금알림전송(입력양식.get시간() + "입금" + 입력양식.get금액() + " (" + 입력양식.get지점() + ")");
        } catch(Exception e) {
            // 입금알림이 나가지 않았다는 로그발생
            // 입금 알림이 나가지 않았으나, 치명적이지 않으므로 로그만 남김.
        }

        return true;
    }
} 