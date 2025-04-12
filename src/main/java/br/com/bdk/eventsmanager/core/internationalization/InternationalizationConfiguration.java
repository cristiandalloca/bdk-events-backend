package br.com.bdk.eventsmanager.core.internationalization;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class InternationalizationConfiguration {

    @Bean
    public MessageSource messageSource() {
        final var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
        final var localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final var localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(getDefaultLocale());
        localeResolver.setSupportedLocales(this.getSupportedLocales());
        return localeResolver;
    }

    private List<Locale> getSupportedLocales() {
        return List.of(getDefaultLocale(), Locale.of("en", "US"), Locale.of("es", "ES"));
    }

    public static Locale getDefaultLocale() {
        return Locale.of("pt", "BR");
    }

    public static TimeZone getDefaultTimeZone() {
        return TimeZone.getTimeZone(ZoneOffset.UTC);
    }

}
