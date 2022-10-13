/**비즈니스 요구 사항 정리
 * 데이터 : 회원ID, 이름
 * 기능 : 회원등록, 조회
 * DB는 선정되지 않는 상황이라고 가정
 */

 // 일반적인 웹 애플리케이션 계층 구조
 Controller -> Service -> repository -> DB
        |         |          |
        |_____> Domain <_____|

/**
 * Controller : 웹 mvc의 컨트롤러 역할
 * Service    : 핵심 비즈니스 로직 구현( ex) 회원간 이름 중복 불가 등)
 * Repository : 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리 -> 단순히 저장된 객체에 대한 접근하는 방식으로 구현
 */
                                (interface)
MemberService -------------> MemberRepository
                                    |
                                    |
                                    |
                                    |
                                    |
                                  Memory
                             MemberRepository

/**
 * 위 그림의 클래스 의존관계 형태로 구현한다.
 * 데이터 저장소가 선정되지 않았기 때문에, 나중에 변경하기 쉽게 interface로 repository 구현
 * 개발을 진행하기위해서 초기 개발 단계에는 메모리 기반의 저장소를 사용한다.
 */

package hello.hellospring.domain;

public class Member {
    private Login id; // system이 정해주는 순번
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}