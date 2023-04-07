package jpashop.jpapractice.dto.member.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpDto {
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, message = "비밀번호는 6자 이상 입력해주세요.")
    private String password;
}
