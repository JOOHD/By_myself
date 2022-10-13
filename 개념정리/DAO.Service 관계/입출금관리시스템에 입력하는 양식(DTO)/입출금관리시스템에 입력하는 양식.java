package net.kurien.demo;

import java.time.LocalDateTime;

public class 입출금관리시스템입력양식 {
    private String 계좌;
    private int 금액;
    private String 지점;
    private LocalDateTime 시간;

    입출금관리시스템입력양식(String 계좌, int 금액, String 지점, LocalDateTime 시간) {
        this.계좌 = 계좌;
        this.금액 = 금액;
        this.지점 = 지점;
        this.시간 = 시간;
    }

    public String get계좌() {
        return 계좌;
    }

    public int get금액() {
        return 금액;
    }

    public String get지점() {
        return 지점;
    }

    public LocalDateTime get시간() {
        return 시간;
    }
}