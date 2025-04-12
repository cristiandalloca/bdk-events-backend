package br.com.bdk.eventsmanager.common;

import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

@UtilityClass
public class ArgumentProviderUtil {

    public static class BlankStringArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(""),
                    Arguments.of(" "),
                    Arguments.of("  ")
            );
        }
    }

    public static class InvalidEmailArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of("c"),
                    Arguments.of(" "),
                    Arguments.of(" c.om.br@d "),
                    Arguments.of("222fasdcc.com.br"),
                    Arguments.of("222@fasdcccombr")
            );
        }
    }
}
