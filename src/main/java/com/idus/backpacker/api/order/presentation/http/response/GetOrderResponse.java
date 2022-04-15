package com.idus.backpacker.api.order.presentation.http.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@ApiModel
@Getter
@Builder
public class GetOrderResponse {
    @ApiModelProperty(
            value = "주문 아이디",
            required = true,
            example = "620abc1c-e184-44ad-a904-b965370460fa")
    private UUID id;

    @ApiModelProperty(
            value = "해당 주문을 진행한 회원 아이디",
            required = true,
            example = "d2f32877-fdf5-442c-87cd-3703bcaa1393")
    private UUID userId;

    @ApiModelProperty(value = "주문 번호", required = true, example = "220406999991")
    private String code;

    @ApiModelProperty(value = "주문한 상품명", required = true, example = "손세정제 (젤타입)")
    private String productName;

    @ApiModelProperty(value = "주문일시", required = true)
    private LocalDateTime orderedAt;

    @ApiModelProperty(value = "생성일시")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "수정일시")
    private LocalDateTime updatedAt;
}
