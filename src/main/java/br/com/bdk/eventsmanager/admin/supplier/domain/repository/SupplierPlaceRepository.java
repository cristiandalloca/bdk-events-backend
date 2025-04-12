package br.com.bdk.eventsmanager.admin.supplier.domain.repository;

import br.com.bdk.eventsmanager.admin.supplier.domain.model.SupplierPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierPlaceRepository extends JpaRepository<SupplierPlace, Long> {

    Optional<SupplierPlace> findByNameIgnoreCaseAndSupplierExternalId(@NonNull String name, @NonNull String externalId);

    @Query("""
        select sp from SupplierPlace sp
        where sp.supplier.externalId = :supplierExternalId
        and sp.supplier.hasEventsPlaces = true
        and (:name is null or upper(sp.name) like concat('%', upper(:name), '%'))
        and (:peopleCapacity is null or sp.maximumCapacityPeople >= :peopleCapacity)
        and (:active is null or sp.active = :active)
    """)
    List<SupplierPlace> findBySupplierExternalId(@NonNull String supplierExternalId,
                                                 @Nullable String name,
                                                 @Nullable Integer peopleCapacity,
                                                 @Nullable Boolean active);

    Optional<SupplierPlace> findByExternalIdAndSupplierExternalId(@NonNull String externalId, @NonNull String supplierExternalId);
}
