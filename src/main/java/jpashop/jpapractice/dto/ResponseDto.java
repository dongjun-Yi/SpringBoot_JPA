package jpashop.jpapractice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//DTO
@Data
public class ResponseDto {
    @ApiModelProperty(value = "필드 값", example = "예시", required = true)
    private String a1;

    @ApiModelProperty(value = "필드 값", example = "예시")
    private String a2;
}