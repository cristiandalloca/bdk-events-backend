package br.com.bdk.eventsmanager.admin.service.domain.repository;

import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ServiceSupplierRepository extends JpaRepository<ServiceSupplier, Long> {

    @Query("""
        select ss from ServiceSupplier ss
            join fetch ss.supplier cp
            join fetch cp.address.city ct
            join fetch ct.state st
        where ss.service.externalId = :externalId
    """)
    Set<ServiceSupplier> findByServiceExternalId(@NonNull String externalId);

    Optional<ServiceSupplier> findByExternalId(@NonNull String externalId);
}
