package hello.hellospring.respository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

/**회원 레포지토리 인터페이스 구현
 * repository 패키지 생성 후 MemberRepository interface를 생성한다.
 * Optional로 리턴값을 wrapping하는 이유는 null값을 편하게 다루기 위해서 사용한다.
 */

public interface MemberRepository {
    // 메소드 생성
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}

