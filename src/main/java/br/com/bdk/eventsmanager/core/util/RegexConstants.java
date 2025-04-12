package br.com.bdk.eventsmanager.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexConstants {

    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PHONE_NUMBER_REGEX = "^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9]\\d)\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$";

}
