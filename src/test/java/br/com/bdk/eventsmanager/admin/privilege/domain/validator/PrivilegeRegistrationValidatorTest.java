package br.com.bdk.eventsmanager.admin.privilege.domain.validator;

import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.privilege.domain.model.exception.DuplicateNamePrivilegeException;
import br.com.bdk.eventsmanager.admin.privilege.domain.repository.PrivilegeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrivilegeRegistrationValidatorTest {

    @InjectMocks
    private PrivilegeRegistrationValidator privilegeRegisterValidator;

    @Mock
    private PrivilegeRepository privilegeRepository;

    private Privilege privilege;

    @BeforeEach
    void setUp() {
        privilege = new Privilege();
        privilege.setName("CADASTRAR_USUARIOS");
        privilege.setDescription("Permite cadastrar usuários");
    }

    @Test
    void shouldValidateWhenNotExistingPrivilege() {
        when(privilegeRepository.findByNameIgnoreCase(privilege.getName())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> privilegeRegisterValidator.validate(privilege));
    }

    @Test
    void shouldValidateWhenExistsPrivilege() {
        ReflectionTestUtils.setField(privilege, "id", 1L);
        when(privilegeRepository.findByNameIgnoreCase(privilege.getName())).thenReturn(Optional.of(privilege));
        assertDoesNotThrow(() -> privilegeRegisterValidator.validate(privilege));
    }

    @Test
    void shouldThrowExceptionWhenExistsOtherPrivilegeWithSameName() {
        ReflectionTestUtils.setField(privilege, "id", 1L);
        var otherPrivilege = new Privilege();
        when(privilegeRepository.findByNameIgnoreCase(privilege.getName())).thenReturn(Optional.of(otherPrivilege));
        var exception = assertThrows(DuplicateNamePrivilegeException.class, () -> privilegeRegisterValidator.validate(privilege));
        assertEquals("Já existe privilégio com nome '%s'".formatted(privilege.getName()), exception.getMessage());
    }
}