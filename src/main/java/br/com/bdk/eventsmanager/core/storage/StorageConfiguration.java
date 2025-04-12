package br.com.bdk.eventsmanager.core.storage;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

    @Bean
    public Storage getStorageService() {
        return StorageOptions.getDefaultInstance().getService();
    }

}
