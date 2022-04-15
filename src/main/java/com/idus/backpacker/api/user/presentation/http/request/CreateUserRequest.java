package com.idus.backpacker.api.user.presentation.http.request;

import com.idus.backpacker.api.kernel.application.validator.MatchString;
import com.idus.backpacker.api.kernel.application.validator.PhoneNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@ApiModel(description = "회원 가입 필드")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @ApiModelProperty(value = "회원 이름 (한글, 영문 대소문자로 20자 까지)", required = true, example = "홍길동")
    @NotBlank
    @Length(max = 20)
    @Pattern(regexp = "^[\uAC00-\uD7A3A-z]+$", message = "이름은 한글, 영문 대소문자만 가능합니다.")
    private String name;

    @ApiModelProperty(value = "회원 닉네임 (영문 소문자로 30자 까지)", required = true, example = "TEST")
    @NotBlank
    @Length(max = 30)
    @Pattern(regexp = "^[a-z]+$", message = "별명은 영문 소문자만 가능합니다.")
    private String nickName;

    @ApiModelProperty(
            value = "비밀번호 (대소문자, 숫자, 특수문자 포함 10자 이상 100자 이하)",
            required = true,
            example = "Test!@340139")
    @NotBlank
    @Length(min = 10, max = 100, message = "비밀번호는 최소 10자로 구성하여야 합니다.")
    @Pattern(
            regexp = "^(?=.*[\\d])(?=.*[A-Z])(?=.*[a-z])(?=.*[\\~!@#$%^&*()])[A-z\\d\\~!@#$%^&*()-_]+$",
            message = "비밀번호 규칙에 맞지 않습니다. 비밀번호는 영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상 포함해야 합니다.")
    private String password;

    @ApiModelProperty(value = "이메일 주소", required = true, example = "test@gmail.com")
    @Email(message = "올바른 이메일 주소가 아닙니다.")
    @Length(max = 100)
    @NotBlank
    private String email;

    @ApiModelProperty(value = "휴대폰 번호 (E.164)", required = true, example = "010-1234-5678")
    @PhoneNumber
    @Length(max = 20)
    private String phoneNumber;

    @ApiModelProperty(value = "성별", allowableValues = "MALE, FEMALE", example = "MALE")
    @MatchString(
            allows = {"MALE", "FEMALE"},
            nullable = true)
    private String sex;
}
