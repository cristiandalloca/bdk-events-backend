package br.com.bdk.eventsmanager.core.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.MissingResourceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MessageUtilTest {

    @Nested
    class WhenGetMessageByKey {

        @Test
        void shouldOk() {
            final String message = MessageUtil.getMessage("NotBlank");
            assertThat(message).isNotBlank();
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "."})
        void shouldReturnKeyWhenNotFoundMessage(final String key) {
            final String message = MessageUtil.getMessage(key);
            assertThat(message).isEqualTo(key);
        }

        @Test
        void shouldThrowExceptionWhenKeyIsNull() {
            assertThatThrownBy(() -> MessageUtil.getMessage(null)).isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    class WhenGetMessageByKeyAndArguments {

        @Test
        void shouldOk() {
            final String message = MessageUtil.getMessage("NotBlank", "**/**");
            assertThat(message).contains("**/**");
        }

        @Test
        void shouldOkIgnoringOtherArguments() {
            final String firstArgument = "**/**";
            final String otherArgument = "--/--";
            final String message = MessageUtil.getMessage("NotBlank", firstArgument, otherArgument);
            assertThat(message)
                    .contains(firstArgument)
                    .doesNotContain(otherArgument);

        }
    }

}