package br.com.bdk.eventsmanager.core.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentValidatorTest {

    private final DocumentValidator documentValidator = new DocumentValidator();

    @Test
    void shouldReturnValidWhenDocumentIsNull() {
        assertThat(documentValidator.isValid(null, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void shouldReturnValidWhenDocumentIsEmptyOrBlank(String document) {
        assertThat(documentValidator.isValid(document, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"20148350127", "364.684.446-91"})
    void shouldReturnValidWhenDocumentIsAValidCpf(String documentCpf) {
        assertThat(documentValidator.isValid(documentCpf, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"20148350117", "364.684.466-91", "2014835127", "364.64.446-91"})
    void shouldReturnInvalidWhenDocumentIsAInvalidCpf(String documentCpf) {
        assertThat(documentValidator.isValid(documentCpf, null)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"23.338.846/0001-31", "45101239000196"})
    void shouldReturnValidWhenDocumentIsAValidCnpj(String documentCnpj) {
        assertThat(documentValidator.isValid(documentCnpj, null)).isTrue();
    }
}