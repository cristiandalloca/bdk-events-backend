package br.com.bdk.eventsmanager.admin.course.domain.repository;

import br.com.bdk.eventsmanager.admin.course.domain.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByNameIgnoreCase(@NonNull String name);

    @Query("""
        select c from Course c
        where (:name is null or upper(c.name) like concat('%', upper(:name), '%'))
        and (:active is null or c.active = :active)
        order by c.name, c.id desc
    """)
    Page<Course> findAllByFilter(@Nullable String name, @Nullable Boolean active, @NonNull Pageable pageable);

    Optional<Course> findByExternalId(@NonNull String externalId);
}
