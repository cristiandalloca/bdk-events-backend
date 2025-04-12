package br.com.bdk.eventsmanager.admin.educationalinstitution.domain.repository;

import br.com.bdk.eventsmanager.admin.city.domain.model.City_;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitution;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitutionType;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitution_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Long> {

    Optional<EducationalInstitution> findByNameIgnoreCase(@NonNull String name);

    @EntityGraph(attributePaths = {EducationalInstitution_.CITY, EducationalInstitution_.CITY + "." + City_.STATE})
    Optional<EducationalInstitution> findByExternalId(@NonNull String externalId);

    @Query("""
        select ei from EducationalInstitution ei
            join fetch ei.city c
            join fetch c.state s
        where (:name is null or upper(ei.name) like concat('%', upper(:name), '%'))
            and (:cityExternalId is null or c.externalId = :cityExternalId)
            and (:type is null or ei.type = :type)
            and (:active is null or ei.active = :active)
        order by ei.name asc, ei.id desc
    """)
    Page<EducationalInstitution> findAllByFilter(@Nullable String name, @Nullable String cityExternalId,
                                                 @Nullable EducationalInstitutionType type, @Nullable Boolean active, Pageable pageable);
}
