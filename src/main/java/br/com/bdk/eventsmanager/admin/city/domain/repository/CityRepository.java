package br.com.bdk.eventsmanager.admin.city.domain.repository;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.model.City_;
import br.com.bdk.eventsmanager.admin.state.domain.model.State_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @EntityGraph(attributePaths = {City_.STATE, City_.STATE + "." + State_.COUNTRY})
    Optional<City> findByExternalId(String externalId);

    @EntityGraph(attributePaths = City_.STATE)
    Optional<City> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    @Query("""
        select ci from City ci
            join fetch ci.state st
            join fetch st.country ct
        where (:externalStateId is null or st.externalId = :externalStateId)
            and (:name is null or upper(ci.name) like concat('%', upper(:name), '%'))
        order by ci.name, ci.id desc
    """)
    Page<City> findAllByStateExternalIdAndName(String externalStateId, String name, Pageable pageable);
}
