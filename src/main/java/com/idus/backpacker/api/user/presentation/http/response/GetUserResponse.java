package com.idus.backpacker.api.user.presentation.http.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "회원 상세 정보 조회")
@Builder
@Getter
public class GetUserResponse {
    @ApiModelProperty(
            value = "회원 아이디",
            required = true,
            example = "4537e6c0-7245-4244-bb8e-05eaa8641efb")
    private final UUID id;

    @ApiModelProperty(value = "회원 이름", required = true, example = "홍길동")
    private final String name;

    @ApiModelProperty(value = "회원 닉네임", required = true, example = "NICKNAME")
    private final String nickName;

    @ApiModelProperty(value = "회원 이메일", required = true, example = "test@gmail.com")
    private final String email;

    @ApiModelProperty(
            value = "회원 휴대폰 번호",
            notes = "회원 휴대폰 번호는 E.164에 맞게 파싱할 수 있으며, 언제나 국가 코드를 포함해서 제공합니다. 언제나 공백은 없는 형태입니다.",
            required = true,
            example = "+821023456789")
    private final String phoneNumber;

    @ApiModelProperty(
            value = "회원 성별",
            notes = "MALE 이라면 남성, FEMALE 이라면 여성. 생물학적 성별을 제공합니다.",
            allowableValues = "MALE, FEMALE")
    private final String sex;

    @ApiModelProperty(value = "회원의 마지막 주문 정보")
    private final Order order;

    @ApiModelProperty(value = "생성일시")
    private final LocalDateTime createdAt;

    @ApiModelProperty(value = "수정일시")
    private final LocalDateTime updatedAt;

    @ApiModel(value = "회원의 마지막 주문 정보")
    @Builder
    @Getter
    public static class Order {
        @ApiModelProperty(
                value = "회원의 마지막 주문 아이디",
                required = true,
                example = "a4358806-9c24-41d1-b519-8228c8381ed4")
        private final UUID orderId;

        @ApiModelProperty(value = "회원의 마지막 주문 코드", required = true, example = "220406999999")
        private final String code;

        @ApiModelProperty(value = "회원의 마지막 주문 상품", required = true, example = "손세정제")
        private final String productName;

        @ApiModelProperty(value = "회원의 마지막 주문일시분초 (UTC)", required = true)
        private final LocalDateTime orderedAt;
    }
}
