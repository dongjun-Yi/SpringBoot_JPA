package jpashop.jpapractice.domain.service;

import jpashop.jpapractice.domain.Member;
import jpashop.jpapractice.dto.BasicResponse;
import jpashop.jpapractice.repository.MemberDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 붙은 필드만 가지고 생성자를 만들어줌ㄴ
public class MemberService {

    //private final MemberRepository memberRepository;
    private final MemberDataJpaRepository memberDataJpaRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberDataJpaRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //List<Member> findMembers = memberRepository.findByName(member.getName());
        List<Member> findMembers = memberDataJpaRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberDataJpaRepository.findAll();
    }

    /**
     * 회원 조회
     */
    public Member findOne(Long id) {
        //return memberRepository.findOne(id);
        return memberDataJpaRepository.findOneById(id);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void update(Long id, String name) {
        Member member = memberDataJpaRepository.findOneById(id);
        member.setName(name);
    }
}
