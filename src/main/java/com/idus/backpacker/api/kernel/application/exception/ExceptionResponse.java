package com.idus.backpacker.api.kernel.application.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@JsonInclude
@ApiModel(value = "Error Response", description = "에러에 대한 결과 정보입니다. 모든 에러를 이 형태로 제공합니다.")
public class ExceptionResponse {
    /** HTTP Status code */
    @ApiModelProperty(
            value = "HTTP 상태값 (정수형). 클라이언트는 본 상태값을 직접 참조할 필요 없으며, message 와 errors 를 통해 문제 내용을 유추해야 합니다.",
            example = "400",
            dataType = "java.lang.Integer",
            required = true)
    @Builder.Default
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    @ApiModelProperty(value = "에러 내용으로, 메세지에는 담기지 않는 상세한 내용(문제가 발생한 필드 등)이 담긴다.", required = true)
    @Builder.Default
    private List<ErrorField> errors = new ArrayList<>();

    @ApiModelProperty(
            value = "발생한 문제에 대해 대략적인 내용을 제공한다. 서버 에러, 또는 보안상 문제가 있는 정보는 Internal Server Error로 제공한다.",
            required = true)
    @Builder.Default
    private String message = "Unknown";

    @ApiModelProperty(value = "문제가 발생한 URI", required = true)
    private String path;

    @ApiModelProperty(value = "문제가 발생한 일시분초(UTC)", required = true)
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(hidden = true)
    @Nullable
    private List<String> stacks;

    @ApiModel
    @Builder
    @Getter
    public static class ErrorField {
        @ApiModelProperty(value = "문제가 발생한 필드(Property)", required = true, example = "email")
        private final String field;

        @ApiModelProperty(value = "문제 내용", required = true, example = "올바른 이메일 주소가 아닙니다.")
        private final String message;
    }
}
