package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optioinal;

/**회원 서비스 개발
 * 서비스의 경우 레포지토리의 접근하는 메서드듣 보다는 복잡한 행동을 수행하는 비지니스 로직을 구현합니다.
 * 마찬가지로 service 패키지 생성후, 아래에 MemberService 클래스를 생성한다.
 * 해당 클래스에서 바로 private final MemberRepository memberRepository = new MemoryMemberRepository();
   로 초기화 하지 않고, 생성자를 통해서 외부에서 객체를 생성하도록 하는 행위를 DI라고 한다.
 */

public class MemberService {
    private final MemberRepository memberRepository;

    //DI(Dependency Injection) MemberRepository를 외부 (MemberServiceTest)에서 주입해준다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById((memberId))
    }

    private void validateDuplicateMember(Member member) {
        // 같은 이름 방지
        memberRepository.findByName(member.getName())
        .ifPresent(m -> {
            throw new IlleglStateException("이미 존재하는 회원입니다.")
        });
    }
}    