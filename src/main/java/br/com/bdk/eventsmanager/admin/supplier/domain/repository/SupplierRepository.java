package br.com.bdk.eventsmanager.admin.supplier.domain.repository;

import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.filter.SupplierFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.trimToNull;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByNameIgnoreCaseAndCompanyExternalId(@NonNull String name, @NonNull String companyExternalId);

    @Query("""
        select s from Supplier s
            join fetch s.company cp
            join fetch s.address.city ct
            join fetch ct.state st
        where s.externalId = :externalId
    """)
    Optional<Supplier> findByExternalId(@NonNull String externalId);

    @Query("""
        select s from Supplier s
            join fetch s.company cp
            join fetch s.address.city ct
            join fetch ct.state st
        where (:companyExternalId is null or cp.externalId = :companyExternalId)
        and (:cityExternalId is null or ct.externalId = :cityExternalId)
        and (:active is null or s.active = :active)
        and (:hasEventsPlaces is null or s.hasEventsPlaces = :hasEventsPlaces)
        and (:name is null or upper(s.name) like concat('%', upper(:name), '%'))
    """)
    Page<Supplier> findAllByFilter(@Nullable String companyExternalId,
                                   @Nullable String cityExternalId,
                                   @Nullable String name,
                                   @Nullable Boolean active,
                                   @Nullable Boolean hasEventsPlaces,
                                   @NonNull Pageable pageable);

    default Page<Supplier> findAllByFilter(SupplierFilter filter, Pageable pageable) {
        return this.findAllByFilter(trimToNull(filter.getCompanyExternalId()), trimToNull(filter.getCityExternalId()),
                trimToNull(filter.getName()), filter.getActive(), filter.getHasEventsPlaces(), pageable);
    }
}