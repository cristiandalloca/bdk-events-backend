package br.com.bdk.eventsmanager.admin.user.domain.repository;

import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.admin.user.domain.model.User_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = User_.COMPANY)
    Optional<User> findByExternalId(String externalId);

    Optional<User> findByLoginIgnoreCase(String login);

    @Query("""
        select u from User u
            join fetch u.company c
        where (:name is null or upper(u.name) like concat('%', upper(:name), '%'))
            and (:login is null or upper(u.login) like concat('%', upper(:login), '%'))
        order by u.name asc, u.id desc
    """)
    Page<User> findAllByNameAndLogin(String name, String login, Pageable pageable);

    @Query("""
        select u from User u
            left join fetch u.profiles p
            left join fetch p.privileges
        where u.externalId = :externalId
    """)
    Optional<User> findByExternalIdWithPrivileges(String externalId);

    Optional<User> findByEmailIgnoreCase(String email);
}
