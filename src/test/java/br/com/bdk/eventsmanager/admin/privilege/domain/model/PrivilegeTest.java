package br.com.bdk.eventsmanager.admin.privilege.domain.model;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrivilegeTest {

    private Privilege privilege;

    @BeforeEach
    void setUp() {
        privilege = new Privilege();
        privilege.setName("CADASTRO_USUARIO.ACESSO");
        privilege.setDescription("Cadastro de usuário - Acesso");
    }

    @Test
    void shouldReturnMajorName() {
        var majorName = privilege.getMajorName();
        assertEquals("CADASTRO_USUARIO", majorName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"CADASTRO_USUARIO_ACESSO", "CADASTROUSUARIOACESSO"})
    void shouldReturnNameWhenNotFoundSeparator(String name) {
        privilege.setName(name);
        assertEquals(name, privilege.getMajorName());
    }

    @Test
    void shouldReturnEmptyWhenNameIsNull() {
        privilege.setName(null);
        assertEquals(StringUtils.EMPTY, privilege.getMajorName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldReturnEmptyWhenNameIsBlank(String name) {
        privilege.setName(name);
        assertEquals(StringUtils.EMPTY, privilege.getMajorName());
    }

    @Test
    void shouldReturnMajorDescription() {
        assertEquals("Cadastro de usuário", privilege.getMajorDescription());
    }

    @Test
    void shouldReturnMinorDescription() {
        assertEquals("Acesso", privilege.getMinorDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Cadastro de usuário . Acesso", "Cadastro de usuário Acesso"})
    void shouldReturnMajorDescriptionWhenNotFoundSeparator(String name) {
        privilege.setDescription(name);
        assertEquals(name, privilege.getMajorDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Cadastro de usuário . Acesso", "Cadastro de usuário Acesso"})
    void shouldReturnMinorDescriptionWhenNotFoundSeparator(String name) {
        privilege.setDescription(name);
        assertEquals(name, privilege.getMinorDescription());
    }

    @Test
    void shouldReturnEmptyMajorDescriptionWhenDescriptionIsNull() {
        privilege.setDescription(null);
        assertEquals(StringUtils.EMPTY, privilege.getMajorDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldReturnEmptyMajorDescriptionWhenDescriptionIsBlank(String description) {
        privilege.setDescription(description);
        assertEquals(StringUtils.EMPTY, privilege.getMajorDescription());
    }

    @Test
    void shouldReturnEmptyMinorDescriptionWhenDescriptionIsNull() {
        privilege.setDescription(null);
        assertEquals(StringUtils.EMPTY, privilege.getMinorDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldReturnEmptyMinorDescriptionWhenDescriptionIsBlank(String description) {
        privilege.setDescription(description);
        assertEquals(StringUtils.EMPTY, privilege.getMinorDescription());
    }
}