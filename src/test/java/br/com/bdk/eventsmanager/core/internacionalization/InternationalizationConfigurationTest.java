package br.com.bdk.eventsmanager.core.internacionalization;

import br.com.bdk.eventsmanager.core.internationalization.InternationalizationConfiguration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneOffset;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class InternationalizationConfigurationTest {

    @InjectMocks
    private InternationalizationConfiguration internationalizationConfiguration;


    @Nested
    class WhenReturnDefaultLocale {

        @Test
        void shouldReturnPortugueseBrazilian() {
            assertEquals(Locale.of("pt", "BR"), internationalizationConfiguration.getDefaultLocale());
        }

    }

    @Nested
    class WhenReturnDefaultTimeZone {

        @Test
        void shouldReturnTimezoneUTC() {
            assertEquals(TimeZone.getTimeZone(ZoneOffset.UTC), internationalizationConfiguration.getDefaultTimeZone());
        }
    }
}