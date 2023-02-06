package jpashop.jpapractice.dto.member;

import jpashop.jpapractice.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
    private String name;
    private Address address;
}
