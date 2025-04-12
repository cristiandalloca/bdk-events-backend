package br.com.bdk.eventsmanager.admin.event.api.v1.model.input;

import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class EventInputTest extends ValidatorTest {

    private EventInput eventInput;

    @BeforeEach
    void setUp() {
        eventInput = new EventInput();
        ReflectionTestUtils.setField(eventInput, "name", "Colação de Grau");
        ReflectionTestUtils.setField(eventInput, "companyId", "dfd197a2-d345-4834-90dc-57867c0e2fa0");
        ReflectionTestUtils.setField(eventInput, "types", new EventType[]{EventType.GRADUATION, EventType.WEDDING});
        ReflectionTestUtils.setField(eventInput, "active", Boolean.TRUE);
    }

    @Test
    void shouldValidateEventInput() {
        var violations = validator.validate(eventInput);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateNameIsNull() {
        validateFieldIsNull(eventInput, "name");
    }

    @Test
    void shouldValidateCompanyIdIsNull() {
        validateFieldIsNull(eventInput, "companyId");
    }

    @Test
    void shouldValidateActiveIsNull() {
        validateFieldIsNull(eventInput, "active");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldValidateNameIsBlank(String name) {
        validateField(eventInput, "name", name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void shouldValidateCompanyIdIsBlank(String companyId) {
        validateField(eventInput, "companyId", companyId);
    }
}