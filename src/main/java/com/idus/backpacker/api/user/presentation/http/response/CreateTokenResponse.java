package com.idus.backpacker.api.user.presentation.http.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(description = "인증 완료 후 토큰 정보 제공")
@Getter
@Builder
public class CreateTokenResponse {
    @ApiModelProperty(
            value = "인증 토큰",
            notes = "기본적인 인증 및 인가에 사용하는 토큰",
            required = true,
            example =
                    "eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoiQUNDRVNTIiwianRpIjoiNjIxNjU1ODktYWRhOC00MjZlLTgzZWEtYzYyYWMzMTgyYWI1IiwiaWF0IjoxNjUwMDA0NzgyLCJleHAiOjE2NTAwMDQ3ODN9.pPJHAmsLw7PU-D8jikoF_LbKcNRmAeShmgXUHljgZ61GkrqsJTCnCDMgNfp7XGX4oweXD7kxcV97BoE-g0ddkA")
    private final String accessToken;

    @ApiModelProperty(
            value = "재인증 토큰",
            notes = "재인증에 사용할 토큰",
            required = true,
            example =
                    "eyJhbGciOiJIUzUxMiJ9.eyJ0eXBlIjoiUkVGUkVTSCIsImp0aSI6IjYyMTY1NTg5LWFkYTgtNDI2ZS04M2VhLWM2MmFjMzE4MmFiNSIsImlhdCI6MTY1MDAwNDc4MiwiZXhwIjoxNjUwMDA0NzgzfQ.lI86z0-sSS6JWQM_FX3WHSu88cidomR2Z0EAnT1G2obroI8B8KBaihXfNYwEXZr2hl1kBNsOw4pUF9lDs4W0Iw")
    private final String refreshToken;

    @ApiModelProperty(value = "인증 토큰의 현재 기준으로부터 만료시간 (초)", required = true, example = "300")
    private final int expiresIn;

    @ApiModelProperty(value = "재인증 토큰의 현재 기준으로부터 만료시간 (초)", required = true, example = "1440")
    private final int refreshExpiresIn;
}
