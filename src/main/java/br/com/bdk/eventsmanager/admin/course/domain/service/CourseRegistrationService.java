package br.com.bdk.eventsmanager.admin.course.domain.service;

import br.com.bdk.eventsmanager.admin.course.domain.model.Course;
import br.com.bdk.eventsmanager.admin.course.domain.model.exception.CourseNotFoundException;
import br.com.bdk.eventsmanager.admin.course.domain.repository.CourseRepository;
import br.com.bdk.eventsmanager.admin.course.domain.validator.CourseRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseRegistrationService implements RegistrationService<Course> {

    private static final String EXCEPTION_MESSAGE_COURSE_IN_USE
            = "Curso de código '%s' não pode ser removido, pois está em uso";
    private final CourseRepository repository;
    private final CourseRegistrationValidator validator;

    @NonNull
    @Override
    @Transactional
    public Course validateAndSave(@NonNull Course course) {
        validator.validate(course);
        return repository.save(course);
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Course findByExternalId(@NonNull String externalId) {
        return this.findByExternalIdOrFail(externalId);
    }

    @Override
    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            repository.delete(this.findByExternalIdOrFail(externalId));
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_COURSE_IN_USE.formatted(externalId));
        }
    }

    private Course findByExternalIdOrFail(@NonNull String externalId) {
        return repository.findByExternalId(externalId)
                .orElseThrow(() -> new CourseNotFoundException(externalId));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<Course> findAll(@Nullable String name, @Nullable Boolean active, @NonNull Pageable pageable) {
        return repository.findAllByFilter(StringUtils.trimToNull(name), active, pageable);
    }
}
