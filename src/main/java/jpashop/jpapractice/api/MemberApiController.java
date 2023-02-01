package jpashop.jpapractice.api;

import jpashop.jpapractice.domain.Address;
import jpashop.jpapractice.domain.Member;
import jpashop.jpapractice.domain.service.MemberService;
import jpashop.jpapractice.dto.MemberResultDto;
import jpashop.jpapractice.dto.BasicResponse;
import jpashop.jpapractice.web.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public ResponseEntity<BasicResponse> members() {
        return memberService.getMembers();
    }

    @PostMapping("/api/members/add")
    public ResponseEntity<MemberResultDto> addMember(@RequestBody HashMap<String, String> param) {
        Member member = new Member();
        member.setName(param.get("name"));
        return memberService.addMember(member);
    }
}
