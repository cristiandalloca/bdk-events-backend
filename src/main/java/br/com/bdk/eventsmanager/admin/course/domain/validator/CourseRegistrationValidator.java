package br.com.bdk.eventsmanager.admin.course.domain.validator;

import br.com.bdk.eventsmanager.admin.course.domain.model.Course;
import br.com.bdk.eventsmanager.admin.course.domain.model.exception.DuplicateNameCourseException;
import br.com.bdk.eventsmanager.admin.course.domain.repository.CourseRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CourseRegistrationValidator implements RegistrationValidator<Course> {

    private final CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public void validate(@NonNull Course course) throws BusinessException {
        final var existingCourse = courseRepository.findByNameIgnoreCase(course.getName());
        if (existingCourse.isPresent() && this.isNotSame(course, existingCourse.get())) {
            throw new DuplicateNameCourseException(course.getName());
        }
    }

}
