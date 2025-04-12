package br.com.bdk.eventsmanager.admin.privilege.domain.service;

import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.privilege.domain.model.exception.PrivilegeNotFoundException;
import br.com.bdk.eventsmanager.admin.privilege.domain.repository.PrivilegeRepository;
import br.com.bdk.eventsmanager.admin.privilege.domain.validator.PrivilegeRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrivilegeRegistrationServiceTest {

    @InjectMocks
    private PrivilegeRegistrationService privilegeRegisterService;

    @Mock
    private PrivilegeRegistrationValidator privilegeRegisterValidator;

    @Mock
    private PrivilegeRepository privilegeRepository;

    private Privilege privilege;

    private static final String EXTERNAL_ID = "112333L";

    @BeforeEach
    void setUp() {
        privilege = new Privilege();
        privilege.setName("CADASTRO_USUARIO");
        privilege.setDescription("Permite cadastrar usuários");
    }

    @Test
    void shouldValidateAndSave() {
        privilegeRegisterService.validateAndSave(privilege);
        inOrder(privilegeRegisterValidator, privilegeRepository);
        verify(privilegeRegisterValidator, times(1)).validate(privilege);
        verify(privilegeRepository,times(1)).save(privilege);
    }

    @Test
    void shouldFindByExternalId() {
        when(privilegeRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(privilege));
        assertDoesNotThrow(() -> privilegeRegisterService.findByExternalId(EXTERNAL_ID));
    }

    @Test
    void shouldThrowExceptionWhenFindByExternalId() {
        when(privilegeRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.empty());
        var exception = assertThrows(PrivilegeNotFoundException.class, () -> privilegeRegisterService.findByExternalId(EXTERNAL_ID));
        assertThat(exception.getMessage())
                .isEqualTo("Não foi encontrado um privilégio com código '%s'".formatted(EXTERNAL_ID));
    }

    @Test
    void shouldRemoveByExternalId() {
        when(privilegeRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(privilege));
        assertDoesNotThrow(() -> privilegeRegisterService.removeByExternalId(EXTERNAL_ID));
        verify(privilegeRepository,times(1)).delete(privilege);
        verify(privilegeRepository,times(1)).flush();
    }

    @Test
    void shouldThrowExceptionWhenRemoveByExternalId() {
        when(privilegeRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(privilege));
        doThrow(DataIntegrityViolationException.class).when(privilegeRepository).delete(privilege);
        var exception = assertThrows(ResourceInUseException.class, () -> privilegeRegisterService.removeByExternalId(EXTERNAL_ID));
        assertThat(exception.getMessage()).isEqualTo("Privilégio de código '%s' não pode ser removido, pois está em uso".formatted(EXTERNAL_ID));
        verify(privilegeRepository,times(0)).flush();
    }

    @Test
    void shouldFindAll() {
        when(privilegeRepository.findAllByDescription(isNull())).thenReturn(List.of(privilege));
        var result = privilegeRegisterService.findAll("");
        assertThat(result.size()).isOne();
    }
}