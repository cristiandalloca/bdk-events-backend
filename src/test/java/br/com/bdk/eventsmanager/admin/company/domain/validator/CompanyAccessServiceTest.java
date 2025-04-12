package br.com.bdk.eventsmanager.admin.company.domain.validator;

import br.com.bdk.eventsmanager.auth.domain.service.LoggedUserService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ValidationAutoConfiguration.class)
@SpringBootTest(classes = CompanyAccessService.class)
class CompanyAccessServiceTest {

    private static final String COMPANY_EXTERNAL_ID = UUID.randomUUID().toString();

    @Autowired
    private CompanyAccessService companyAccessService;

    @MockitoBean
    private LoggedUserService loggedUserService;

    @Nested
    class WhenValidateAccessByCompanyExternalId {

        @Test
        void doesNotShouldThrowExceptionWhenUserLoggedIsAdmin() {
            when(loggedUserService.isAdmin()).thenReturn(Boolean.TRUE);
            assertThatNoException()
                    .isThrownBy(() -> companyAccessService.validate(COMPANY_EXTERNAL_ID));
            verify(loggedUserService, never()).getUserCompanyExternalId();
        }

        @Test
        void doesNotShouldThrowExceptionWhenUserLoggedCompanyExternalIdIsSameParameter() {
            when(loggedUserService.isAdmin()).thenReturn(Boolean.FALSE);
            when(loggedUserService.getUserCompanyExternalId()).thenReturn(COMPANY_EXTERNAL_ID);
            assertThatNoException()
                    .isThrownBy(() -> companyAccessService.validate(COMPANY_EXTERNAL_ID));
        }

        @Test
        void shouldThrowExceptionWhenParameterIsNull() {
            assertThatThrownBy(() -> companyAccessService.validate(null))
                    .isInstanceOf(ConstraintViolationException.class)
                    .hasMessage("validate.companyExternalId: não deve estar vazio");
            verifyNoInteractions(loggedUserService);
        }

        @Test
        void shouldThrowExceptionWhenParameterIsEmpty() {
            assertThatThrownBy(() -> companyAccessService.validate(""))
                    .isInstanceOf(ConstraintViolationException.class)
                    .hasMessage("validate.companyExternalId: não deve estar vazio");
            verifyNoInteractions(loggedUserService);
        }

        @Test
        void shouldThrowExceptionAccessDeniedException() {
            when(loggedUserService.isAdmin()).thenReturn(Boolean.FALSE);
            when(loggedUserService.getUserCompanyExternalId()).thenReturn(UUID.randomUUID().toString());

            assertThatThrownBy(() -> companyAccessService.validate(COMPANY_EXTERNAL_ID))
                    .isInstanceOf(AccessDeniedException.class)
                    .hasMessage("Usuário logado não possui acesso a empresa");
        }
    }

    @Nested
    class WhenGetLoggedCompanyExternalIdOrNull {

        @Test
        void shouldReturnNullWhenLoggedUserIsAdmin() {
            when(loggedUserService.isAdmin()).thenReturn(Boolean.TRUE);
            assertThat(companyAccessService.getLoggedCompanyExternalIdOrNullIfIsAdmin())
                    .isNull();
            verify(loggedUserService, never()).getUserCompanyExternalId();
        }

        @Test
        void shouldReturnLoggedCompanyExternalIdWhenIsNotAdmin() {
            when(loggedUserService.isAdmin()).thenReturn(Boolean.FALSE);
            when(loggedUserService.getUserCompanyExternalId()).thenReturn(COMPANY_EXTERNAL_ID);
            assertThat(companyAccessService.getLoggedCompanyExternalIdOrNullIfIsAdmin())
                    .isEqualTo(COMPANY_EXTERNAL_ID);

        }
    }
}