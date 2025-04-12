package br.com.bdk.eventsmanager.core.util;

import br.com.bdk.eventsmanager.core.internationalization.InternationalizationConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
public class MessageUtil {

    private MessageUtil() {}

    public static String getMessage(String key) {
        return getMessage(key, (Object) null);
    }

    public static String getMessage(String key, Object... arguments) {
        String message = key;
        try {
            message = MessageFormat.format(getBundle().getString(key), arguments);
        } catch (MissingResourceException e) {
            log.error(e.getMessage(), e);
        }

        return message;
    }

    private static ResourceBundle getBundle() {
        final Locale currentDefaultLocale = Locale.getDefault();
        final Locale locale = currentDefaultLocale != null ? currentDefaultLocale : InternationalizationConfiguration.getDefaultLocale();
        return ResourceBundle.getBundle("messages", locale);
    }
}
