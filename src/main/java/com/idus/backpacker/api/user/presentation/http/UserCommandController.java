package com.idus.backpacker.api.user.presentation.http;

import com.idus.backpacker.api.user.application.command.CreateUserCommand;
import com.idus.backpacker.api.user.presentation.http.request.CreateUserRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
        value = "회원 가입 및 수정",
        tags = {"user"})
@RestController
@RequestMapping(
        value = "/users",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class UserCommandController {

    @Value("${application.host}")
    private String host;

    private final CreateUserCommand createUserCommand;

    @ApiOperation(value = "회원 가입", notes = "새로이 회원을 추가(가입)한다.")
    @PostMapping
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequest request) {
        final var userId = UUID.randomUUID();

        this.createUserCommand.invoke(
                CreateUserCommand.Model.builder()
                        .id(userId)
                        .name(request.getName())
                        .nickName(request.getNickName())
                        .password(request.getPassword())
                        .phoneNumber(request.getPhoneNumber())
                        .email(request.getEmail())
                        .sex(request.getSex())
                        .build());

        return ResponseEntity.created(URI.create(String.format("://%s/users/%s", this.host, userId)))
                .build();
    }
}
