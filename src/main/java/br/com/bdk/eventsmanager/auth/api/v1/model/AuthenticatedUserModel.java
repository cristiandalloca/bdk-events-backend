package br.com.bdk.eventsmanager.auth.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class AuthenticatedUserModel {

    @Schema(example = Example.IDENTIFIER, description = Description.USER_IDENTIFIER)
    private String id;

    @Schema(example = Example.USER_NAME, description = Description.USER_NAME)
    private String name;

    @Schema(example = Example.USER_FIRST_LETTER_FIRST_NAME_LAST_NAME, description = Description.USER_FIRST_LETTER_FIRST_NAME_LAST_NAME)
    private String initialsName;

    @Schema(example = Example.BOOLEAN, description = Description.USER_UPDATE_PASSWORD_NEXT_LOGIN)
    private boolean updatePasswordNextLogin;

    @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
    private Company company;

    @Schema(example = Example.PRIVILEGES_NAME_ARRAY, description = Description.PRIVILEGE_NAME)
    @Builder.Default
    private Set<String> privileges = new HashSet<>();

    @Getter
    @Builder
    public static class Company {

        @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
        private String id;

        @Schema(example = Example.COMPANY_NAME, description = Description.COMPANY_NAME)
        private String name;

    }
}