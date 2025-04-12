package br.com.bdk.eventsmanager.admin.service.domain.repository;

import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceCostType;
import br.com.bdk.eventsmanager.admin.service.domain.model.Service_;
import br.com.bdk.eventsmanager.admin.service.domain.model.dto.ServiceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.stripToNull;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findByNameIgnoreCaseAndCompanyExternalId(@NonNull String name, @NonNull String companyExternalId);

    @EntityGraph(attributePaths = Service_.COMPANY)
    Optional<Service> findByExternalId(@NonNull String externalId);

    default Page<Service> findAllByFilter(@NonNull ServiceFilter filter, @NonNull Pageable pageable) {
        return findAllByFilter(stripToNull(filter.getName()), filter.getCostType(), filter.getApplyBDI(), filter.getApplySellerCommission(),
                filter.getActive(), stripToNull(filter.getCompanyExternalId()), stripToNull(filter.getEventExternalIdToIgnore()), pageable);
    }

    @Query("""
            select s from Service s
                join fetch s.company c
            where (:companyExternalId is null or c.externalId = :companyExternalId)
                and (:name is null or upper(s.name) like concat('%', upper(:name), '%'))
                and (:costType is null or s.costType = :costType)
                and (:applyBDI is null or s.applyBDI = :applyBDI)
                and (:applySellerCommission is null or s.applySellerCommission = :applySellerCommission)
                and (:active is null or s.active = :active)
                and (:eventExternalIdToIgnore is null or not exists (select es.id from EventService es where es.service.id = s.id and es.event.externalId = :eventExternalIdToIgnore))
            order by s.name, s.id desc
            """)
    Page<Service> findAllByFilter(@Nullable String name, @Nullable ServiceCostType costType, @Nullable Boolean applyBDI,
                                  @Nullable Boolean applySellerCommission, @Nullable Boolean active, @Nullable String companyExternalId,
                                  @Nullable String eventExternalIdToIgnore, @NonNull Pageable pageable);

}
