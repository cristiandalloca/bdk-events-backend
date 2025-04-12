package br.com.bdk.eventsmanager.admin.city.api.v1.controller;

import br.com.bdk.eventsmanager.admin.city.api.v1.assembler.CityStateCountryAssembler;
import br.com.bdk.eventsmanager.admin.city.api.v1.disassembler.CityInputDisassembler;
import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateCountryModel;
import br.com.bdk.eventsmanager.admin.city.api.v1.model.input.CityInput;
import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.model.exception.CityNotFoundException;
import br.com.bdk.eventsmanager.admin.city.domain.service.CityRegistrationService;
import br.com.bdk.eventsmanager.admin.user.domain.service.UserRegistrationService;
import br.com.bdk.eventsmanager.auth.domain.service.JwtService;
import br.com.bdk.eventsmanager.auth.domain.service.LoggedUserService;
import br.com.bdk.eventsmanager.auth.domain.service.UserDetailsImplService;
import br.com.bdk.eventsmanager.common.EnvironmentMock;
import br.com.bdk.eventsmanager.core.api.exceptionhandler.ProblemType;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import br.com.bdk.eventsmanager.core.logging.domain.service.RequestLogRegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(CityRegisterController.class)
@MockitoBean(types = {RequestLogRegisterService.class,
        JwtService.class,
        UserDetailsImplService.class,
        JpaMetamodelMappingContext.class,
        LoggedUserService.class,
        UserRegistrationService.class})
class CityRegisterControllerTest {

    private static final String URL = "/v1/cities";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CityStateCountryAssembler cityStateCountryAssembler;

    @MockitoBean
    private CityRegistrationService cityRegisterService;

    @MockitoBean
    private CityInputDisassembler cityInputDisassembler;

    @Nested
    class WhenSearchByExternalId {

        @Test
        @SneakyThrows
        void shouldReturnOk() {
            final var externalId = RandomStringUtils.secure().nextAlphanumeric(10);

            final var city = EnvironmentMock.mock(City.class);
            when(cityRegisterService.findByExternalId(externalId))
                    .thenReturn(city);

            final var cityStateCountryModel = EnvironmentMock.mock(CityStateCountryModel.class);
            when(cityStateCountryAssembler.toModel(city)).thenReturn(cityStateCountryModel);

            mockMvc.perform(get(URL + "/{id}", externalId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(cityStateCountryModel.getId()));
        }

        @Test
        @SneakyThrows
        void shoudReturnNotFound() {
            final var externalId = RandomStringUtils.secure().nextAlphanumeric(10);
            doThrow(CityNotFoundException.class).when(cityRegisterService).findByExternalId(externalId);
            mockMvc.perform(get(URL + "/{id}", externalId))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.type").value(ProblemType.RESOURCE_NOT_FOUND.name()));
        }
    }

    @Nested
    class WhenCreateCity {

        private CityInput cityInput;

        @BeforeEach
        void setUp() {
            cityInput = new CityInput();
            ReflectionTestUtils.setField(cityInput, "name", RandomStringUtils.secure().nextAlphabetic(255));
            ReflectionTestUtils.setField(cityInput, "stateId", RandomStringUtils.secure().nextAlphanumeric(36));
        }

        @Test
        @SneakyThrows
        void shouldReturnCreated() {
            var city = EnvironmentMock.mock(City.class);
            when(cityInputDisassembler.toEntity(any(CityInput.class))).thenReturn(city);

            var citySaved = EnvironmentMock.mock(City.class);
            when(cityRegisterService.validateAndSave(city)).thenReturn(citySaved);

            var cityStateCountryModel = EnvironmentMock.mock(CityStateCountryModel.class);
            when(cityStateCountryAssembler.toModel(citySaved)).thenReturn(cityStateCountryModel);


            mockMvc.perform(post(URL).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(cityStateCountryModel.getId()));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenNameIsBlank() {
            ReflectionTestUtils.setField(cityInput, "name", StringUtils.SPACE);

            mockMvc.perform(post(URL).with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("name"))
                    .andExpect(jsonPath("$.fields.length()").value(1));

        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenNameIsNull() {
            ReflectionTestUtils.setField(cityInput, "name", null);

            mockMvc.perform(post(URL).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("name"))
                    .andExpect(jsonPath("$.fields.length()").value(1));

        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenNameSizeIsMoreThan255() {
            ReflectionTestUtils.setField(cityInput, "name", RandomStringUtils.secure().nextAlphabetic(256));

            mockMvc.perform(post(URL).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("name"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenNameContainsNumbers() {
            ReflectionTestUtils.setField(cityInput, "name", RandomStringUtils.secure().nextAlphanumeric(255));

            mockMvc.perform(post(URL).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("name"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenStateIdIsNull() {
            ReflectionTestUtils.setField(cityInput, "stateId", null);

            mockMvc.perform(post(URL).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("stateId"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenStateIdIsBlank() {
            ReflectionTestUtils.setField(cityInput, "stateId", StringUtils.SPACE);

            mockMvc.perform(post(URL).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("stateId"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenStateIdSizeIsMoreThan36() {
            ReflectionTestUtils.setField(cityInput, "stateId", RandomStringUtils.secure().nextAlphabetic(37));

            mockMvc.perform(post(URL).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("stateId"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

    }

    @Nested
    class WhenUpdateCity {

        private String externalId;
        private CityInput cityInput;

        @BeforeEach
        void setUp() {
            cityInput = new CityInput();
            externalId = UUID.randomUUID().toString();
            ReflectionTestUtils.setField(cityInput, "name", RandomStringUtils.secure().nextAlphabetic(255));
            ReflectionTestUtils.setField(cityInput, "stateId", RandomStringUtils.secure().nextAlphanumeric(36));
        }

        @Test
        @SneakyThrows
        void shouldReturnOk() {
            var city = EnvironmentMock.mock(City.class);
            when(cityRegisterService.findByExternalId(externalId)).thenReturn(city);

            var citySaved = EnvironmentMock.mock(City.class);
            when(cityRegisterService.validateAndSave(city)).thenReturn(citySaved);

            var cityStateCountryModel = EnvironmentMock.mock(CityStateCountryModel.class);
            when(cityStateCountryAssembler.toModel(citySaved)).thenReturn(cityStateCountryModel);

            mockMvc.perform(put(URL + "/{id}", externalId).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(cityStateCountryModel.getId()));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenNameIsBlank() {
            ReflectionTestUtils.setField(cityInput, "name", StringUtils.SPACE);

            mockMvc.perform(put(URL+ "/{id}", externalId).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("name"))
                    .andExpect(jsonPath("$.fields.length()").value(1));

        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenNameIsNull() {
            ReflectionTestUtils.setField(cityInput, "name", null);

            mockMvc.perform(put(URL+ "/{id}", externalId).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("name"))
                    .andExpect(jsonPath("$.fields.length()").value(1));

        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenNameSizeIsMoreThan255() {
            ReflectionTestUtils.setField(cityInput, "name", RandomStringUtils.secure().nextAlphabetic(256));

            mockMvc.perform(put(URL+ "/{id}", externalId).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("name"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenNameContainsNumbers() {
            ReflectionTestUtils.setField(cityInput, "name", RandomStringUtils.secure().nextAlphanumeric(255));

            mockMvc.perform(put(URL+ "/{id}", externalId).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("name"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenStateIdIsNull() {
            ReflectionTestUtils.setField(cityInput, "stateId", null);

            mockMvc.perform(put(URL+ "/{id}", externalId).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("stateId"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenStateIdIsBlank() {
            ReflectionTestUtils.setField(cityInput, "stateId", StringUtils.SPACE);

            mockMvc.perform(put(URL+ "/{id}", externalId).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("stateId"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }

        @Test
        @SneakyThrows
        void shouldReturnBadRequestWhenStateIdSizeIsMoreThan36() {
            ReflectionTestUtils.setField(cityInput, "stateId", RandomStringUtils.secure().nextAlphabetic(37));

            mockMvc.perform(put(URL+ "/{id}", externalId).with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(cityInput)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.type").value(ProblemType.INVALID_DATA.name()))
                    .andExpect(jsonPath("$.fields[0].name").value("stateId"))
                    .andExpect(jsonPath("$.fields.length()").value(1));
        }


    }

    @Nested
    class WhenFindAllCities {

        @Test
        @SneakyThrows
        void shouldReturnOk() {
            String nameFilter = RandomStringUtils.secure().nextAlphabetic(256);
            String externalStateIdFilter = RandomStringUtils.secure().nextAlphanumeric(37);

            final var cities = EnvironmentMock.mockList(City.class, 5);
            final var citiesPage = new PageImpl<>(cities);
            when(cityRegisterService.findAll(eq(externalStateIdFilter), eq(nameFilter), any(Pageable.class)))
                    .thenReturn(citiesPage);

            final var citiesModel = EnvironmentMock.mockList(CityStateCountryModel.class, 5);
            final var citiesModelPage = new PageImpl<>(citiesModel);
            when(cityStateCountryAssembler.toPageModel(citiesPage)).thenReturn(new PageModel<>(citiesModelPage));

            mockMvc.perform(get(URL).queryParams(getQueryParamsFindAll(nameFilter, externalStateIdFilter)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(5));

        }

        @Test
        @SneakyThrows
        void shouldReturnOkWhenDontSendParams() {

            final var cities = EnvironmentMock.mockList(City.class, 5);
            final var citiesPage = new PageImpl<>(cities);
            when(cityRegisterService.findAll(isNull(), isNull(), any(Pageable.class)))
                    .thenReturn(citiesPage);

            final var citiesModel = EnvironmentMock.mockList(CityStateCountryModel.class, 5);
            final var citiesModelPage = new PageImpl<>(citiesModel);
            when(cityStateCountryAssembler.toPageModel(citiesPage)).thenReturn(new PageModel<>(citiesModelPage));

            mockMvc.perform(get(URL))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.length()").value(5));

        }

        private MultiValueMap<String, String> getQueryParamsFindAll(String nameFilter, String stateIdFilter) {
            var multiValueMap = new LinkedMultiValueMap<String, String>();
            multiValueMap.put("name", Collections.singletonList(nameFilter));
            multiValueMap.put("stateId", Collections.singletonList(stateIdFilter));
            multiValueMap.put("page", Collections.singletonList("1"));
            multiValueMap.put("size", Collections.singletonList("25"));
            return multiValueMap;
        }

    }

    @Nested
    class WhenRemoveByExternalId {

        @Test
        @SneakyThrows
        void shouldReturnNoContent() {

            final var externalId = RandomStringUtils.secure().nextAlphanumeric(36);
            mockMvc.perform(
                    delete(URL + "/{id}", externalId).with(csrf()))
                    .andExpect(status().isNoContent());
            verify(cityRegisterService, only()).removeByExternalId(externalId);

        }
    }

}