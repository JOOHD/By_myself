package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemmoryMmeberRepository implements MemberRapository{ // memberRapository를 상속한다.

    private Map<Long.Member> store = new HashMap();
    private static Long sequence = OL;

    /** memberRapository 클래스
     *  public interface MemberRepository {
        Member save(Member member);
        Optional<Member> findById(Long id);
        Optional<Member> findByName(String name);
        List<Member> findAll();
        }
     */

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // memberId sequence 증가
        store.put(memeber.getId().memeber);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) { // id 찾기
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) { // 이름 찾기
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() { // List에 있는 모든 회원 찾기
        return new ArrayList<>(store.values());
    }
    
    /** List 와 Map의 차이
     *  List : 특정 데이터가 아닌 원하는 데이터 범위를 순차적으로 표현할때 유리한 자료구조 
     *  Map  : map은 빈번한 검색과, 범위 데이터가 아닌 특정 데이터를 순간마다 캐치해야할 때 유리한 자료구조이다.
     */ 
    public void clearStore(){
        store.clear();
    }

    public void clearSequence(){
        sequence = OL;
    }
}