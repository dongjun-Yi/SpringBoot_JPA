package jpashop.jpapractice.domain.service;

import jpashop.jpapractice.domain.Member;
import jpashop.jpapractice.dto.BasicResponse;
import jpashop.jpapractice.dto.MemberResultDto;
import jpashop.jpapractice.repository.MemberDataJpaRepository;
import jpashop.jpapractice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<BasicResponse> getMembers() {
        List<Member> members = memberDataJpaRepository.findAll();
        List<MemberResultDto> collect = members.stream()
                .map(m -> new MemberResultDto(m.getName()))
                .collect(Collectors.toList());
        BasicResponse response = new BasicResponse(collect.size(), collect);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<MemberResultDto> addMember(Member member) {
        validateDuplicateMember(member);
        memberDataJpaRepository.save(member);
        MemberResultDto dtoMember = new MemberResultDto(member.getName(), member.getId());
        return new ResponseEntity<>(dtoMember, HttpStatus.OK);
    }
}
