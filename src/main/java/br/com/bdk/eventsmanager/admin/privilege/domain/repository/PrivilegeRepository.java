package br.com.bdk.eventsmanager.admin.privilege.domain.repository;

import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByExternalId(String externalId);

    Optional<Privilege> findByNameIgnoreCase(String name);

    @Query("""
        select p from Privilege p
            where :description is null or upper(p.description) like concat('%', upper(:description), '%')
    """)
    List<Privilege> findAllByDescription(String description);
}
