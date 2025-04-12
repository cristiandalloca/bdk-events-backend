package br.com.bdk.eventsmanager.admin.supplier.domain.model.filter;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
public class SupplierFilter {

    @Nullable
    private String name;

    @Nullable
    private String cityExternalId;

    @Nullable
    private String companyExternalId;

    @Nullable
    private Boolean active;

    @Nullable
    private Boolean hasEventsPlaces;

}
