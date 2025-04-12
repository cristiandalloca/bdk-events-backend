package br.com.bdk.eventsmanager.auth.domain.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {

    private String accessToken;

    private long expiresIn;

}
