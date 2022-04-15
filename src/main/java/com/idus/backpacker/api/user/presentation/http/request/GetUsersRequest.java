package com.idus.backpacker.api.user.presentation.http.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ApiModel(description = "사용자를 조회할 때 사용할 필터")
public class GetUsersRequest {
    @Pattern(regexp = "^[\uAC00-\uD7A3A-z]+$", message = "이름은 한글, 영문 대소문자만 가능합니다.")
    @Length(max = 20, message = "이름은 20자를 초과할 수 없습니다.")
    @ApiModelProperty(value = "회원 이름", allowEmptyValue = true, example = "홍길동")
    private String name;

    @Email(message = "정상적인 이메일 주소가 아닙니다.")
    @Length(max = 100, message = "이메일 주소는 100자를 초과할 수 없습니다.")
    @ApiModelProperty(value = "회원 이메일", allowEmptyValue = true, example = "test@gmail.com")
    private String email;
}
