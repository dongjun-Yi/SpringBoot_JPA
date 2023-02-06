package jpashop.jpapractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BasicResponse<T> {
    //private Integer count;
    private T data;
}
