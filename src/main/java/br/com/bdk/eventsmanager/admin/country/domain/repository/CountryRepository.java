package br.com.bdk.eventsmanager.admin.country.domain.repository;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
 
    Optional<Country> findByExternalId(String externalId);

    Optional<Country> findByNameIgnoreCase(String name);

    Optional<Country> findByAcronymIgnoreCase(String acronym);

    boolean existsByExternalId(String externalId);

    @Query("""
        select c from Country c
            where :name is null or upper(c.name) like concat('%', upper(:name), '%')
            order by c.name, c.id desc
    """)
    Page<Country> findAllByName(String name, Pageable pageable);
}
