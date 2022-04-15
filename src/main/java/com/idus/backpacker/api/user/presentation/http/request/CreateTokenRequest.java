package com.idus.backpacker.api.user.presentation.http.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "로그인 필드")
@Getter
@Setter
public class CreateTokenRequest {
    @ApiModelProperty(value = "회원 이메일", required = true, example = "test@gmail.com")
    @Email(message = "올바른 이메일 주소가 아닙니다.")
    @NotBlank
    private String email;

    @ApiModelProperty(value = "회원 비밀번호", required = true, example = "test1234%^&*!TEST")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
