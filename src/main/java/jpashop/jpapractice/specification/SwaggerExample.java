package jpashop.jpapractice.specification;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jpashop.jpapractice.dto.ResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//Controller
@Api(tags = "컨트롤러이름")
@RestController
public class SwaggerExample {

    @Operation(summary = "요약", description = "설명")
    @ApiResponse(code = 200, message = "ok")
    @PostMapping("/ex/")
    public ResponseDto exampleMethod() {
        return new ResponseDto();
    }
}