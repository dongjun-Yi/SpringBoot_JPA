package jpashop.jpapractice.api;

import jpashop.jpapractice.domain.Member;
import jpashop.jpapractice.domain.service.MemberService;
import jpashop.jpapractice.dto.member.create.CreateMemberRequest;
import jpashop.jpapractice.dto.member.create.CreateMemberResponse;
import jpashop.jpapractice.dto.member.update.UpdateMemberRequest;
import jpashop.jpapractice.dto.member.update.UpdateMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 등록 API
     */
    @PostMapping("/api/members")
    public ResponseEntity<CreateMemberResponse> saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new ResponseEntity<>(new CreateMemberResponse(id), HttpStatus.OK);
    }

    @PutMapping("/api/members/{id}")
    public ResponseEntity<UpdateMemberResponse> updateMember(@RequestBody @Valid UpdateMemberRequest request,
                                                             @PathVariable("id") Long id) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new ResponseEntity<>(new UpdateMemberResponse(findMember.getId(), findMember.getName()), HttpStatus.OK);
    }
}
