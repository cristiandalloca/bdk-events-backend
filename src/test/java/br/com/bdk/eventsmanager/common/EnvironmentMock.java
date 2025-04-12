package br.com.bdk.eventsmanager.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jeasy.random.EasyRandom;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnvironmentMock {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    public static <T> T mock(final Class<T> clazz) {
        return EASY_RANDOM.nextObject(clazz);
    }

    public static <T> List<T> mockList(final Class<T> clazz) {
        return mockList(clazz, 1);
    }

    public static <T> List<T> mockList(final Class<T> clazz, final Integer size) {
        return EASY_RANDOM.objects(clazz, size).toList();
    }

}
