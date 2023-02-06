package jpashop.jpapractice.dto.member.create;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateMemberResponse {
    private Long id;

    public CreateMemberResponse(Long id) {
        this.id = id;
    }
}
