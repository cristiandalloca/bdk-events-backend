package br.com.bdk.eventsmanager.admin.course.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.course.api.v1.model.input.CourseInput;
import br.com.bdk.eventsmanager.admin.course.domain.model.Course;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CourseInputDisassembler implements InputDisassembler<CourseInput, Course> {

    @Override
    public Course toEntity(@NonNull CourseInput input) {
        var course = new Course();
        this.copyToEntity(input, course);
        return course;
    }

    @Override
    public void copyToEntity(@NonNull CourseInput input, @NonNull Course course) {
        course.setName(input.getName());
        course.setActive(input.getActive());
    }
}
