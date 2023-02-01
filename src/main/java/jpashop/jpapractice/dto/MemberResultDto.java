package jpashop.jpapractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MemberResultDto {
    private String name;
    private Long id;

    public MemberResultDto(String name) {
        this.name = name;
    }
}
