package br.com.bdk.eventsmanager.auth.domain.repository;

import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.auth.domain.model.PrivilegeDto;
import br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends CrudRepository<User, Long> {

    String USER_DETAILS_DTO = """
                select new br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto(
                u.id,
                u.externalId,
                u.name,
                u.login,
                u.password,
                c.externalId,
                c.name,
                c.active,
                u.updatePasswordNextLogin,
                u.active,
                u.admin)
            from User u
                inner join u.company c
            """;

    @Query(USER_DETAILS_DTO +
            "where upper(u.login) = upper(:loginOrEmail) OR upper(u.email) = upper(:loginOrEmail)")
    Optional<UserDetailsDto> findByLoginOrEmail(@NonNull String loginOrEmail);

    @Query(USER_DETAILS_DTO +
            "where u.externalId = :externalId")
    Optional<UserDetailsDto> findByExternalId(@NonNull String externalId);

    @Query("""
        select new br.com.bdk.eventsmanager.auth.domain.model.PrivilegeDto(
            pg.id,
            pg.externalId,
            pg.name,
            pg.description)
        from User u
            inner join u.profiles pf
            inner join pf.privileges pg
        where u.id = :id
    """)
    List<PrivilegeDto> findAllPrivilegesById(Long id);
}
