package jpashop.jpapractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BasicResponse<T> {
    private String message;

    private Integer count;
    private T data;

    public BasicResponse(Integer count, T data) {
        this.count = count;
        this.data = data;
    }

    public BasicResponse(String message) {
        this.message = message;
    }
}
