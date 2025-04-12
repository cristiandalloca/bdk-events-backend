package br.com.bdk.eventsmanager.admin.eventservice.domain.repository;

import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.CompanyInvitationType_;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventService;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventService_;
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
public interface EventServiceRepository extends JpaRepository<EventService, Long> {

    @EntityGraph(attributePaths = {EventService_.EVENT, EventService_.SERVICE, EventService_.COMPANY_INVITATION_TYPE,
            EventService_.COMPANY_INVITATION_TYPE + "." + CompanyInvitationType_.INVITATION_TYPE})
    Optional<EventService> findByExternalId(String eventServiceExternalId);

    @Query("""
            select es from EventService es
                inner join fetch es.service s
                inner join fetch es.event e
                left join fetch es.companyInvitationType cit
                left join fetch cit.invitationType it
            where e.externalId = :externalEventId
            and (:serviceName is null or upper(s.name) like concat('%', upper(:serviceName), '%'))
            order by s.name, es.id desc
            """)
    Page<EventService> findAllByEventExternalId(@NonNull String externalEventId, @Nullable String serviceName, @NonNull Pageable pageable);

    Optional<EventService> findByEventExternalIdAndServiceExternalId(String externalEventId, String externalServiceId);

}