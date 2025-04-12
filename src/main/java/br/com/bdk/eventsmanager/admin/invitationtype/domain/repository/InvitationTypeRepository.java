package br.com.bdk.eventsmanager.admin.invitationtype.domain.repository;

import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.InvitationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationTypeRepository extends JpaRepository<InvitationType, Long> {

    Optional<InvitationType> findByExternalId(@NonNull String externalId);

    @Query("""
        select it from InvitationType it
        where :name is null or (upper(it.name) like concat('%', upper(:name), '%'))
    """)
    List<InvitationType> findAllByNameContainingIgnoreCase(@Nullable String name);

    Optional<InvitationType> findByNameIgnoreCase(@NonNull String name);
}
