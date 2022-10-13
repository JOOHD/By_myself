package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.Test;

import static org.assertj.core.api.Assertion.assertThat;
import static org.junit.jupiter.api.Asertioins;

/**회원 서비스 테스트
 *  레포지토리와 마찬가지로 직접 패키지를 생성 후 클래스를 만들어도 되고 단축기(command + shift + T)를 통해서 생성해도 된다.
 * @BeforeEach : 각 테스트 실행전에 호출된다. 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고 의존관계도 새로 맺게한다.(DI)
 * 테스트 케이스의 메소드 이름은 직관적으로 한글로 작성해도 상관없다.
 */

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    void BeforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberSerivce = new MemberService(memberRepository); // memberRepository를 memberService에 주입해준다.
    }

    @AfterEach
    void AfterEach() {
        memberRepository.clearStore();
    }
    
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
        //when
        Long saveId = memberService.join(member);
        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복회원방지(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        
        //when
        memberService.join(member1);
        IllegalStateException exception = assertThrows(IllegalStateException.classs, () -> memberService.join(member2));
        assertThat(exception.getMessage()).isEqualsTo("이미 존재하는 회원입니다.");

        /*try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}