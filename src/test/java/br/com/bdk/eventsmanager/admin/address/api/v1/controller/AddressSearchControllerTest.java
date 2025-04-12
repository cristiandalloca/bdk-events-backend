package br.com.bdk.eventsmanager.admin.address.api.v1.controller;

import br.com.bdk.eventsmanager.admin.address.api.v1.AddressModelAssembler;
import br.com.bdk.eventsmanager.admin.address.domain.service.AddressSearchService;
import br.com.bdk.eventsmanager.admin.user.domain.service.UserRegistrationService;
import br.com.bdk.eventsmanager.auth.domain.service.JwtService;
import br.com.bdk.eventsmanager.auth.domain.service.LoggedUserService;
import br.com.bdk.eventsmanager.auth.domain.service.UserDetailsImplService;
import br.com.bdk.eventsmanager.core.api.exceptionhandler.ProblemType;
import br.com.bdk.eventsmanager.core.internationalization.InternationalizationConfiguration;
import br.com.bdk.eventsmanager.core.logging.domain.service.RequestLogRegisterService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(AddressSearchController.class)
@MockitoBean(types = {
        RequestLogRegisterService.class,
        JwtService.class,
        UserDetailsImplService.class,
        JpaMetamodelMappingContext.class,
        LoggedUserService.class,
        UserRegistrationService.class,
        InternationalizationConfiguration.class})
class AddressSearchControllerTest {

    private static final String URL = "/v1/addresses";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressSearchService addressSearchService;

    @MockitoBean
    private AddressModelAssembler addressModelAssembler;

    @Nested
    class WhenSearchingAddress {

        @Test
        @SneakyThrows
        void shouldReturnOk() {
            final var postalCode = "88058320";
            mockMvc.perform(get(URL).param("postalCode", postalCode))
                    .andExpect(status().isOk());
            verify(addressSearchService).findByPostalCode(postalCode);
            verify(addressModelAssembler).toModel(any());
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenWithoutPostalCodeParam() {
            final var detailMessage = "Required request parameter 'postalCode' for method parameter type String is not present";
            mockMvc.perform(
                    get(URL)
            ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.type").value(ProblemType.MANDATORY_PARAMETER.name()))
                    .andExpect(jsonPath("$.title").value(ProblemType.MANDATORY_PARAMETER.getTitle()))
                    .andExpect(jsonPath("$.detail").value(detailMessage))
                    .andExpect(jsonPath("$.userMessage").value(detailMessage))
                    .andExpect(jsonPath("$.timestamp").exists());

            verifyNoInteractions(addressSearchService);
            verifyNoInteractions(addressModelAssembler);
        }

        final String expectedDetailMessage = "One or more fields are invalid. Please enter correctly and try again";

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenPostalCodeIsBlank() {
            mockMvc.perform(get(URL).param("postalCode", StringUtils.EMPTY))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.title").value(ProblemType.INVALID_DATA.getTitle()))
                    .andExpect(jsonPath("$.detail").value(expectedDetailMessage))
                    .andExpect(jsonPath("$.userMessage").value(expectedDetailMessage))
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.fields", hasSize(1)))
                    .andExpect(jsonPath("$.fields[0].name").value("postalCode"))
                    .andExpect(jsonPath("$.fields[0].userMessage").value("must not be blank"));

            verifyNoInteractions(addressSearchService);
            verifyNoInteractions(addressModelAssembler);
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenPostalCodeContainsLetters() {
            mockMvc.perform(get(URL).param("postalCode", RandomStringUtils.secure().nextAlphanumeric(8)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.title").value(ProblemType.INVALID_DATA.getTitle()))
                    .andExpect(jsonPath("$.detail").value(expectedDetailMessage))
                    .andExpect(jsonPath("$.userMessage").value(expectedDetailMessage))
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.fields", hasSize(1)))
                    .andExpect(jsonPath("$.fields[0].name").value("postalCode"))
                    .andExpect(jsonPath("$.fields[0].userMessage").value("Only numbers are allowed"));

            verifyNoInteractions(addressSearchService);
            verifyNoInteractions(addressModelAssembler);
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenPostalCodeIsNotContainsExactSize() {
            mockMvc.perform(get(URL).param("postalCode", RandomStringUtils.secure().nextNumeric(9)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.title").value(ProblemType.INVALID_DATA.getTitle()))
                    .andExpect(jsonPath("$.detail").value(expectedDetailMessage))
                    .andExpect(jsonPath("$.userMessage").value(expectedDetailMessage))
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.fields", hasSize(1)))
                    .andExpect(jsonPath("$.fields[0].name").value("postalCode"))
                    .andExpect(jsonPath("$.fields[0].userMessage").value("Tamanho inválido. O tamanho deve ser 8 caracteres"));

            verifyNoInteractions(addressSearchService);
            verifyNoInteractions(addressModelAssembler);

        }

        @Test
        @SneakyThrows
        @WithAnonymousUser
        void shouldReturnUnauthorized() {
            final var postalCode = "88058320";
            mockMvc.perform(get(URL).param("postalCode", postalCode))
                    .andExpect(status().isUnauthorized());

            verifyNoInteractions(addressSearchService);
            verifyNoInteractions(addressModelAssembler);

        }

    }

}