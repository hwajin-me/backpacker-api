package com.idus.backpacker.api.order.presentation.http.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class GetOrderRequest {
    @ApiModelProperty(
            value = "회원 아이디",
            notes = "주문 조회 필터로서 회원 서비스의 회원 도메인의 회원 아이디",
            required = true,
            example = "6f27c244-9144-4bd7-99c5-b9a2b0883656")
    @NotNull(message = "회원 아이디는 필수입니다.")
    private UUID userId;
}
