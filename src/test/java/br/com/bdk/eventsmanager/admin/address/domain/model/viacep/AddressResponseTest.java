package br.com.bdk.eventsmanager.admin.address.domain.model.viacep;

import br.com.bdk.eventsmanager.common.EnvironmentMock;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AddressResponseTest {

    @Nested
    class WhenVerifyIfExistsCityAndStateInfo {

        @Test
        void shouldReturnTrueWhenExistsCityAndStateInfo() {
            final var addressResponse = EnvironmentMock.mock(AddressResponse.class);
            assertThat(addressResponse.existsCityAndStateInfo()).isTrue();
        }

        @ParameterizedTest
        @ArgumentsSource(AddressResponseArgumentProvider.class)
        void shouldReturnFalseWhenNotExistsCityAndStateInfo(final AddressResponse addressResponse) {
            assertThat(addressResponse.existsCityAndStateInfo()).isFalse();
        }

        private static class AddressResponseArgumentProvider implements ArgumentsProvider {

            @Override
            public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
                final var addressResponseWithNullCityName = EnvironmentMock.mock(AddressResponse.class);
                ReflectionTestUtils.setField(addressResponseWithNullCityName, "cityName", null);

                final var addressResponseWithEmptyCityName = EnvironmentMock.mock(AddressResponse.class);
                ReflectionTestUtils.setField(addressResponseWithEmptyCityName, "cityName", StringUtils.SPACE);

                final var addressResponseWithNullStateName = EnvironmentMock.mock(AddressResponse.class);
                ReflectionTestUtils.setField(addressResponseWithNullStateName, "stateName", null);

                final var addressResponseWithEmptyStateName = EnvironmentMock.mock(AddressResponse.class);
                ReflectionTestUtils.setField(addressResponseWithEmptyStateName, "stateName", StringUtils.SPACE);

                final var addressResponseWithNullStateAcronym = EnvironmentMock.mock(AddressResponse.class);
                ReflectionTestUtils.setField(addressResponseWithNullStateAcronym, "stateAcronym", null);

                final var addressResponseWithEmptyStateAcronym = EnvironmentMock.mock(AddressResponse.class);
                ReflectionTestUtils.setField(addressResponseWithEmptyStateAcronym, "stateAcronym", null);

                return Stream.of(
                        Arguments.of(addressResponseWithNullCityName),
                        Arguments.of(addressResponseWithEmptyCityName),
                        Arguments.of(addressResponseWithNullStateName),
                        Arguments.of(addressResponseWithEmptyStateName),
                        Arguments.of(addressResponseWithNullStateAcronym),
                        Arguments.of(addressResponseWithEmptyStateAcronym)
                );
            }
        }
    }
}