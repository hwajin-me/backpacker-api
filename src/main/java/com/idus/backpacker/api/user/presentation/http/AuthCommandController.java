package com.idus.backpacker.api.user.presentation.http;

import com.idus.backpacker.api.kernel.application.exception.ExceptionResponse;
import com.idus.backpacker.api.user.application.service.UserTokenService;
import com.idus.backpacker.api.user.application.service.payload.CreateTokenByRefreshTokenPayload;
import com.idus.backpacker.api.user.application.service.payload.CreateTokenPayload;
import com.idus.backpacker.api.user.presentation.http.request.CreateTokenRequest;
import com.idus.backpacker.api.user.presentation.http.response.CreateTokenResponse;
import io.swagger.annotations.*;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(
        value = "인증",
        tags = {"auth"})
@RestController
@RequestMapping(
        value = "/auth",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AuthCommandController {
    private final UserTokenService service;

    @ApiOperation(value = "인증 진행 (토큰 발급)", notes = "Bearer 토큰을 발급하여 무상태 인증을 진행한다.")
    @PostMapping
    @PreAuthorize("isAnonymous()")
    @ApiResponses({
        @ApiResponse(code = 200, message = "인증 완료"),
    })
    public ResponseEntity<CreateTokenResponse> createToken(@Valid CreateTokenRequest request) {
        final var token =
                this.service.createToken(
                        CreateTokenPayload.builder()
                                .email(request.getEmail())
                                .password(request.getPassword())
                                .build());

        return ResponseEntity.ok(
                CreateTokenResponse.builder()
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .expiresIn(token.getExpiresIn())
                        .refreshExpiresIn(token.getRefreshExpiresIn())
                        .build());
    }

    @ApiOperation(value = "재인증 진행", notes = "Refresh Token을 이용하여 재인증을 진행한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "재인증 완료"),
        @ApiResponse(code = 401, message = "재인증 토큰으로 인증 실패", response = ExceptionResponse.class),
    })
    @PostMapping("/-/refresh")
    public ResponseEntity<Object> createTokenByRefreshToken(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String refreshToken) {
        final var token =
                this.service.createTokenByRefreshToken(
                        CreateTokenByRefreshTokenPayload.builder()
                                .refreshToken(this.getToken(refreshToken))
                                .build());

        return ResponseEntity.ok(
                CreateTokenResponse.builder()
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .expiresIn(token.getExpiresIn())
                        .refreshExpiresIn(token.getRefreshExpiresIn())
                        .build());
    }

    @ApiOperation(value = "로그아웃", notes = "토큰을 모두 파기하여 로그아웃을 수행한다.")
    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @ApiResponses({
        @ApiResponse(code = 204, message = "토큰 파기 완료 (로그아웃 성공)"),
        @ApiResponse(
                code = 400,
                message = "토큰 파기 오류 (이미 파기되었거나 알 수 없는 요청)",
                response = ExceptionResponse.class),
        @ApiResponse(code = 401, message = "인증 실패 (이미 로그아웃 상태)", response = ExceptionResponse.class)
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> revokeToken(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String token) {
        this.service.revokeToken(this.getToken(token));

        return ResponseEntity.noContent().build();
    }

    private String getToken(String originalTokenHeader) {
        return originalTokenHeader.replaceAll("^(bearer|Bearer) ", "");
    }
}
