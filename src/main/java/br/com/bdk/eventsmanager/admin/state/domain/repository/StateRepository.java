package br.com.bdk.eventsmanager.admin.state.domain.repository;

import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.model.State_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @EntityGraph(attributePaths = State_.COUNTRY)
    Optional<State> findByExternalId(String externalId);

    Optional<State> findByNameIgnoreCase(String name);

    Optional<State> findByAcronymIgnoreCase(String acronym);

    @Query("""
        select s from State s
            join fetch s.country c
        where (:countryExternalId is null or c.externalId = :countryExternalId)
            and (:name is null or upper(s.name) like concat('%', upper(:name), '%'))
        order by s.name, s.id desc
    """)
    Page<State> findAllByCountryExternalIdAndName(String countryExternalId, String name, Pageable pageable);

    boolean existsByExternalId(String externalId);
}