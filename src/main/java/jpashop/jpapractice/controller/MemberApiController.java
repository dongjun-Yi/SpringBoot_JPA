package jpashop.jpapractice.controller;

import jpashop.jpapractice.domain.Member;
import jpashop.jpapractice.domain.service.MemberService;
import jpashop.jpapractice.dto.MemberResultDto;
import jpashop.jpapractice.dto.MemberResultForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public MemberResultForm members() {
        List<Member> members = memberService.findMembers();
        List<MemberResultDto> collect = members.stream()
                .map(m -> new MemberResultDto(m.getName()))
                .collect(Collectors.toList());
        return new MemberResultForm(collect.size(), collect);
    }
}
