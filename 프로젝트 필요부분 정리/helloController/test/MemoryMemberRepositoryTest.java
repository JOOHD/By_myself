/**회원 레포지토리 테스트 케이스 작성
 * 기존까지 해오던 방법의 테스트는 곤솔창에 값을 직접 출력하거나, 자바의 main 메서드를 직접 실행하여
 * 테스트 해보았는데 이는 동시에 여러가지의 테스트 케이스를 진행해보지 못할 뿐더러 시간도 오래 걸리기 때문에
 * 현업에서는 JUnit4를 사용한 테스트 케이스를 구현후 실행하는 방식을 사용한다.
 */

 /**회원 레포지토리 메모리 구현체 테스트
  * -src/test/java 하위에 repository 패키지를 생성한다.
  * -이후 MemoryMemberRepositoryTest 클래스를 생성한다.
  * -각각의 메소드 위에 @Test 어노테이션으로 테스트 메소드라는 것을 명시한다.
  * @AfterEach 의 경우 다양한 테스트를 진행할 경우 레포지토리가 초기화 되지 않으면 중복되는 요소 때문에
  * 테스트가 실패할 경우를 방지해주는 어노테이션이다. 즉 각각의 테스트 케이스가 끝날때마다 afterEach()를 호출해서
  * 레포지토리를 초기화 시켜 준다. 여기서는 메모리 DB에 저장된 데이터를 삭제한다. 
  */
package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 만약 해당 테스트 클래스를 먼저 만들고 MemoryMemberRepository를 구현하면 TOD 방식이다.
class MemmoryMmeberRepositoryTest {
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach // 메서드 호출이 끝날 때 마다 호출되는 메서드(레포지토리 초기화, 메모리 DB 저장된 데이터 삭제)
    public void afterEach(){
        memberRepository.clearStore();
        memberRepository.clearSequence();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("brido");

        memberRepository.save(member);

        Member result = memberRepository.findById(member.getId()).get();

        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findById(){
        
        Member member1 = new Member();
        memberRepository.save(member1);

        Member member2 = new Member();
        memberRepository.save(member2);

        Member result = memberRepository.findById(1L).get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findByName(){
        
        Member member1 = new Member();
        member1.setName("spring1");
        memberRepository.save(member1);

        Member member1 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        Member reslt = memberRepository.findByName("spring").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        
        Member member1 = new Member();
        member1.setName("spring1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberRepository.save(member2);

        List<Member> result = memberRepository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}