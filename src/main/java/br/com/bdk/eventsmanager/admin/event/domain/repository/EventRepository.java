package br.com.bdk.eventsmanager.admin.event.domain.repository;

import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event_;
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
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByNameIgnoreCaseAndCompanyExternalId(@NonNull String name, @NonNull String companyExternalId);

    @EntityGraph(attributePaths = {Event_.COMPANY, Event_.TYPES})
    Optional<Event> findByExternalId(@NonNull String externalId);

    @Query("""
            select e from Event e
                join fetch e.company c
                left join fetch e.types t
            where (:name is null or upper(e.name) like concat('%', upper(:name), '%'))
                and (:companyExternalId is null or c.externalId = :companyExternalId)
                and (:type is null or t in (:type))
                and (:active is null or e.active = :active)
            order by e.name asc, e.id desc
            """)
    Page<Event> findAllByNameAndTypeAndActive(@Nullable String name, @Nullable String companyExternalId, @Nullable EventType type, @Nullable Boolean active, @NonNull Pageable pageable);
}
