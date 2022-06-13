package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// @Repository - spring bean test
public class MemoryMemberRepository implements MemberRepository {

    // 실무에서는 동시성 문제가 있어서 해당 변수들을 다른방법으로 고려해봐야한다.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

     /*
     * interface에 있는 method? 오버라이드
      */
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //null 체크
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
       return  store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

}
