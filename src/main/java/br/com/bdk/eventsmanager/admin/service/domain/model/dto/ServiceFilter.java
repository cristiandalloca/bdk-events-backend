package br.com.bdk.eventsmanager.admin.service.domain.model.dto;

import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceCostType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServiceFilter {

    private String name;
    private ServiceCostType costType;
    private Boolean applyBDI;
    private Boolean applySellerCommission;
    private Boolean active;
    private String companyExternalId;
    private String eventExternalIdToIgnore;
}