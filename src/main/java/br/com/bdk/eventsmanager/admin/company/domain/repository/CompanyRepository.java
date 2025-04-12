package br.com.bdk.eventsmanager.admin.company.domain.repository;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByDocument(String document);

    @Query("""
        select cp from Company cp
            join fetch cp.address.city ct
            join fetch ct.state st
        where cp.externalId = :externalId
    """)
    Optional<Company> findByExternalId(String externalId);

    @Query("""
        select cp from Company cp
            join fetch cp.address.city ct
            join fetch ct.state st
        where
            (:nameOrBusinessName is null or (upper(cp.name) like concat('%', upper(:nameOrBusinessName), '%')
                                    or upper(cp.businessName) like concat('%', upper(:nameOrBusinessName), '%')))
            and (:externalId is null or cp.externalId = :externalId)
        order by cp.name, cp.id desc
    """)
    Page<Company> findAllByNameOrBusinessName(@Nullable String externalId, @Nullable String nameOrBusinessName,
                                              @NonNull Pageable pageable);
}
