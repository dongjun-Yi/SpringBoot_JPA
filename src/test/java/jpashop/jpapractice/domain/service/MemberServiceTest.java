package jpashop.jpapractice.domain.service;

import jpashop.jpapractice.domain.Address;
import jpashop.jpapractice.domain.Member;
import jpashop.jpapractice.repository.MemberDataJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberServiceTest {
    @Autowired
    MemberDataJpaRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long id = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOneById(id));
    }

    @Test//(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws IllegalStateException {
        // given
        Member member1 = new Member();
        member1.setName("kim1");
        Member member2 = new Member();
        member2.setName("kim1");

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        });
    }

    @Test
    public void BaseEntity() {
        Member member = new Member();
        member.setName("Hi");
        Address address = new Address("서울", "거리", "123123");
        member.setAddress(address);

        Long findMemberId = memberService.join(member);

        assertThat(findMemberId).isEqualTo(member.getId());
    }

}