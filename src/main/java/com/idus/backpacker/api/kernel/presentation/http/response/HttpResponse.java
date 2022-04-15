package com.idus.backpacker.api.kernel.presentation.http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "RESTful Response", description = "JSON/XML RESTful(Resourceful) Response")
public class HttpResponse<T> {
    @NotNull private final T data;

    private final PageMeta meta;

    public static <T> HttpResponse<T> of(@NonNull T data) {
        return new HttpResponse<T>(data);
    }

    public static <T> HttpResponse<T> of(@NonNull T data, @NonNull Page<?> page) {
        return new HttpResponse<>(data, page);
    }

    private HttpResponse(@NonNull T data) {
        this.data = data;
        this.meta = null;
    }

    private HttpResponse(@NonNull T data, @NonNull Page<?> page) {
        this.data = data;
        this.meta = this.pageableToMeta(page);
    }

    private PageMeta pageableToMeta(Page<?> page) {
        return PageMeta.builder()
                .total(page.getTotalElements())
                .perPage(page.getSize())
                .currentPage(page.getNumber())
                .lastPage(page.getTotalPages())
                .current(page.getNumberOfElements())
                .build();
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @ApiModel(value = "RESTful PageMeta", description = "Page Meta Information")
    public static class PageMeta {
        @ApiModelProperty(
                value = "총 원소(아이템, 리소스) 개수",
                notes = "페이지를 포함하지 않고, 해당 API로 조회했을 때 최대 값.",
                example = "9999")
        private final long total;

        @ApiModelProperty(value = "페이지당 반환할 원소 개수", example = "20")
        private final int perPage;

        @ApiModelProperty(value = "현재 페이지 번호", example = "1")
        private final int currentPage;

        @ApiModelProperty(value = "마지막 페이지 번호", example = "500")
        private final int lastPage;

        @ApiModelProperty(value = "현재 페이지에 존재하는 원소 수", example = "20")
        private final int current;
    }
}
