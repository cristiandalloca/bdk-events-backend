package br.com.bdk.eventsmanager.auth.api.v1.assembler;

import br.com.bdk.eventsmanager.auth.api.v1.model.AuthenticatedUserModel;
import br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.upperCase;

@Component
public class AuthenticatedUserModelAssembler implements ModelAssembler<UserDetailsDto, AuthenticatedUserModel> {

    private static final String REGEX_FIRST_LETTER_NAME_FIRST_LETTER_LAST_NAME = "^\\s*([a-zA-Z]).*\\s+([a-zA-Z])\\S+$";

    @Override
    @NonNull
    public AuthenticatedUserModel toModel(@NonNull UserDetailsDto dto) {
        var name = dto.getName();
        return AuthenticatedUserModel.builder()
                .id(dto.getExternalId())
                .name(name)
                .updatePasswordNextLogin(dto.isUpdatePasswordNextLogin())
                .company(AuthenticatedUserModel.Company.builder()
                        .id(dto.getCompanyExternalId())
                        .name(dto.getCompanyName())
                        .build())
                .initialsName(this.getInitialsName(name))
                .privileges(dto.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()))
                .build();
    }

    private String getInitialsName(String name) {
        final var initials = name.replaceAll(REGEX_FIRST_LETTER_NAME_FIRST_LETTER_LAST_NAME, "$1$2");
        return initials.length() > 2 ? upperCase(name.substring(0, 1)) : upperCase(initials);
    }
}
