package br.com.bdk.eventsmanager.admin.profile.domain.repository;

import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile;
import br.com.bdk.eventsmanager.admin.profile.domain.model.Profile_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByNameIgnoreCaseAndCompanyId(String name, Long companyId);

    @EntityGraph(attributePaths = {Profile_.COMPANY, Profile_.PRIVILEGES})
    Optional<Profile> findByExternalId(String externalId);

    @Query("""
        select p from Profile p
            join fetch p.company c
        where (:name is null or upper(p.name) like concat('%', :name, '%'))
            and (:companyExternalId is null or c.externalId = :companyExternalId)
        order by p.name, p.id desc
    """)
    Page<Profile> findAllByNameAndCompanyExternalId(String name, String companyExternalId, Pageable pageable);

    Optional<Profile> findByExternalIdAndCompanyExternalId(String externalId, String companyExternalId);

}

