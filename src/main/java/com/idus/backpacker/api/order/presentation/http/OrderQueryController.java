package com.idus.backpacker.api.order.presentation.http;

import com.idus.backpacker.api.kernel.presentation.http.response.HttpResponse;
import com.idus.backpacker.api.order.application.query.OrderQuery;
import com.idus.backpacker.api.order.application.query.field.OrderField;
import com.idus.backpacker.api.order.presentation.http.request.GetOrderRequest;
import com.idus.backpacker.api.order.presentation.http.response.GetOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = "/orders",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Api(
        value = "주문 정보 조회",
        tags = {"order"})
public class OrderQueryController {
    private final OrderQuery query;

    @ApiOperation(
            value = "단일 회원의 주문 목록 조회",
            notes =
                    "주문 목록을 조회합니다. 단일 회원에 대한 조회임에도 QueryString 으로 전달해야 합니다. (/orders/-/{userId} 형태도 가능하나, 필터 개념이 더 적합하다 판단했습니다).")
    @GetMapping
    public ResponseEntity<HttpResponse<List<GetOrderResponse>>> getOrders(
            @Valid GetOrderRequest request, Pageable pageable) {
        final var results =
                this.query.findBy(OrderField.builder().userId(request.getUserId()).build(), pageable);

        return ResponseEntity.ok(
                HttpResponse.of(
                        results
                                .map(
                                        order ->
                                                GetOrderResponse.builder()
                                                        .id(order.getId().toUuid())
                                                        .userId(order.getUserId().toUuid())
                                                        .code(order.getCode().toString())
                                                        .productName(order.getProductName().toString())
                                                        .orderedAt(order.getOrderedAt())
                                                        .createdAt(order.getCreatedAt())
                                                        .updatedAt(order.getUpdatedAt())
                                                        .build())
                                .toList(),
                        results));
    }
}
