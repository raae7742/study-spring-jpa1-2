package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)             // 좀 더 최적화됨 - 읽기만 실행
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepositoryOld memberRepositoryOld;

    // 회원 가입
    @Transactional                              // default(readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member);        // 중복 회원 검증
        memberRepositoryOld.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepositoryOld.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepositoryOld.findAll();
    }

    // id로 회원 조회
    public Member findOne(Long id) {
        return memberRepositoryOld.findOne(id);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepositoryOld.findOne(id);
        member.setName(name);
    }
}
