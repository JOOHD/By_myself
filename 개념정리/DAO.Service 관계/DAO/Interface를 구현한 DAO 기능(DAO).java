package net.kurien.demo;

import org.springframework.stereotype.Repository;

@Repository
public class 입출금관리DAO implements 입출금관리DAO인터페이스 {
    @Override
    public boolean insert(입출금관리시스템입력양식 입력양식) {
        은행계좌Entity 은행계좌 = new 은행계좌Entity(입력양식.get계좌(), 입력양식.get금액(), 입력양식.get지점(), 입력양식.get시간());
        // return 은행데이터베이스.insert(은행계좌); 이와같은 형태로 DB에 Insert
        return true;
    }
}