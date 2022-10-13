package net.kurien.demo;

public class 입출금전표 {
    private String 계좌;
    private int 금액;

    입출금전표(String 계좌, int 금액) {
        this.계좌 = 계좌;
        this.금액 = 금액;
    }

    public String get계좌() {
        return 계좌;
    }

    public int get금액() {
        return 금액;
    }
}