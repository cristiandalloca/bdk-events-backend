package br.com.bdk.eventsmanager;

import br.com.bdk.eventsmanager.core.internationalization.InternationalizationConfiguration;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Locale;
import java.util.TimeZone;

@EnableConfigurationProperties
@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@EnableAsync
public class EventsManagerApplication {

    public static void main(String[] args) {
		SpringApplication.run(EventsManagerApplication.class, args);
	}

	@PostConstruct
	void setDefaults() {
		TimeZone.setDefault(InternationalizationConfiguration.getDefaultTimeZone());
		Locale.setDefault(InternationalizationConfiguration.getDefaultLocale());
	}

}
