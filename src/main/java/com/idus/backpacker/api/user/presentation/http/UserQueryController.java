package com.idus.backpacker.api.user.presentation.http;

import com.idus.backpacker.api.kernel.application.exception.ExceptionResponse;
import com.idus.backpacker.api.kernel.presentation.http.response.HttpResponse;
import com.idus.backpacker.api.user.application.query.UserQuery;
import com.idus.backpacker.api.user.application.query.field.FindUserField;
import com.idus.backpacker.api.user.presentation.http.request.GetUsersRequest;
import com.idus.backpacker.api.user.presentation.http.response.GetUserResponse;
import com.idus.backpacker.api.user.presentation.http.response.GetUsersResponse;
import com.idus.backpacker.core.user.domain.user.UserSex;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
        value = "회원 정보 조회",
        tags = {"user"})
@ApiResponses({
    @ApiResponse(code = 200, message = "조회 성공"),
    @ApiResponse(code = 400, message = "쿼리 스트링이 올바르지 않음", response = ExceptionResponse.class),
    @ApiResponse(code = 500, message = "서버에 문제가 발생함", response = ExceptionResponse.class)
})
@RestController
@RequestMapping(
        value = "/users",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class UserQueryController {
    private final UserQuery query;

    @ApiOperation(
            value = "여러 회원 목록 조회",
            notes =
                    "여러 회원의 목록을 조회하고, 이름과 이메일 주소로 필터링 할 수 있다. 삭제한 회원은 리스트에 나오지 않는다.\n"
                            + "기본 정렬은 수정일시의 내림차순이다.")
    @GetMapping
    public ResponseEntity<HttpResponse<List<GetUsersResponse>>> getUsers(
            @Valid GetUsersRequest request, Pageable pageable) {
        final var result =
                this.query.findBy(
                        FindUserField.builder().name(request.getName()).email(request.getEmail()).build(),
                        pageable);

        return ResponseEntity.ok(
                HttpResponse.of(
                        result.stream()
                                .map(
                                        item ->
                                                GetUsersResponse.builder()
                                                        .id(item.getId().toUuid())
                                                        .name(item.getName().toString())
                                                        .nickName(item.getNickName().toString())
                                                        .email(item.getEmail().toString())
                                                        .phoneNumber(item.getPhoneNumber().toString())
                                                        .sex(
                                                                Optional.ofNullable(item.getSex())
                                                                        .map(UserSex::toString)
                                                                        .orElse(null))
                                                        .order(
                                                                Optional.ofNullable(item.getOrder())
                                                                        .map(
                                                                                order ->
                                                                                        GetUsersResponse.Order.builder()
                                                                                                .orderId(order.getOrderId().toUuid())
                                                                                                .productName(order.getProductName())
                                                                                                .orderedAt(order.getOrderedAt())
                                                                                                .build())
                                                                        .orElse(null))
                                                        .createdAt(item.getCreatedAt())
                                                        .updatedAt(item.getUpdatedAt())
                                                        .build())
                                .collect(Collectors.toList()),
                        result));
    }

    @ApiOperation(value = "단일 회원 상세 정보 조회", notes = "단일 회원의 상세 정보를 조회한다.")
    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse<GetUserResponse>> getUser(@PathVariable("id") UUID id) {
        final var result = this.query.findUserBy(id);

        return ResponseEntity.ok(
                HttpResponse.of(
                        GetUserResponse.builder()
                                .id(result.getId().toUuid())
                                .name(result.getName().toString())
                                .nickName(result.getNickName().toString())
                                .email(result.getEmail().toString())
                                .phoneNumber(result.getPhoneNumber().toString())
                                .sex(Optional.ofNullable(result.getSex()).map(UserSex::toString).orElse(null))
                                .order(
                                        Optional.ofNullable(result.getOrder())
                                                .map(
                                                        order ->
                                                                GetUserResponse.Order.builder()
                                                                        .orderId(order.getOrderId().toUuid())
                                                                        .code(order.getCode())
                                                                        .productName(order.getProductName())
                                                                        .orderedAt(order.getOrderedAt())
                                                                        .build())
                                                .orElse(null))
                                .createdAt(result.getCreatedAt())
                                .updatedAt(result.getUpdatedAt())
                                .build()));
    }
}
