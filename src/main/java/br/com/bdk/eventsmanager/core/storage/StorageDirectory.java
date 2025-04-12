package br.com.bdk.eventsmanager.core.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StorageDirectory {

    SERVICE_PHOTO("service/photo"),
    COMPANY_LOGO("company/logo"),
    COMPANY_SIGNATURE("company/signature");

    private final String directory;
}
