package br.com.bdk.eventsmanager.admin.user.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.input.UserInput;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.input.UserWithCompanyInput;
import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserInputDisassembler implements InputDisassembler<UserInput, User> {

    @Override
    public User toEntity(@NonNull UserInput input) {
        User user = new User();
        copyToEntity(input, user);
        return user;
    }

    @Override
    public void copyToEntity(@NonNull UserInput input, @NonNull User user) {
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        if (input instanceof UserWithCompanyInput userWithCompanyInput) {
            user.setCompany(new Company(userWithCompanyInput.getCompanyId()));
        }
        user.setLogin(input.getLogin());
        user.setActive(input.isActive());
    }
}
