package jpashop.jpapractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResultForm<T> {

    private int count;
    private T data;
}
