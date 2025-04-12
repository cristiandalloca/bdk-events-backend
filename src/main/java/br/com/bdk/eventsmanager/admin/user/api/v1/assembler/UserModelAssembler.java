package br.com.bdk.eventsmanager.admin.user.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserModel;
import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserModelAssembler implements ModelAssembler<User, UserModel> {

    private final UserCompanyModelAssembler userCompanyModelAssembler;

    @NonNull
    @Override
    public UserModel toModel(@NonNull User user) {
        return UserModel.builder()
                .id(user.getExternalId())
                .name(user.getName())
                .login(user.getLogin())
                .email(user.getEmail())
                .active(user.isActive())
                .company(userCompanyModelAssembler.toModel(user.getCompany()))
                .build();
    }
}
