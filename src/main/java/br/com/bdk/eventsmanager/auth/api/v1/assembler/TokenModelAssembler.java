package br.com.bdk.eventsmanager.auth.api.v1.assembler;

import br.com.bdk.eventsmanager.auth.api.v1.model.TokenModel;
import br.com.bdk.eventsmanager.auth.domain.model.dto.TokenDto;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TokenModelAssembler implements ModelAssembler<TokenDto, TokenModel> {

    @Override
    @NonNull
    public TokenModel toModel(@NonNull TokenDto dto) {
        return TokenModel.builder()
                .accessToken(dto.getAccessToken())
                .expiresIn(dto.getExpiresIn())
                .build();
    }
}
