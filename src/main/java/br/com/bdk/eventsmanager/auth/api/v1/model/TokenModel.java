package br.com.bdk.eventsmanager.auth.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenModel {

    @Schema(example = SpringDocConstantsUtil.Example.TOKEN)
    private String accessToken;

    @Schema(example = SpringDocConstantsUtil.Example.TOKEN_TYPE)
    @Builder.Default
    private String type = "bearer";

    @Schema(example = SpringDocConstantsUtil.Example.TOKEN_EXPIRES_IN)
    private long expiresIn;
}
