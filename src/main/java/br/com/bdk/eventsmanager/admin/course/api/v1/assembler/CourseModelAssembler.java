package br.com.bdk.eventsmanager.admin.course.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.course.api.v1.model.CourseModel;
import br.com.bdk.eventsmanager.admin.course.domain.model.Course;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseModelAssembler implements ModelAssembler<Course, CourseModel> {

    @NonNull
    @Override
    public CourseModel toModel(@NonNull Course course) {
        return CourseModel.builder()
                .id(course.getExternalId())
                .name(course.getName())
                .active(course.isActive())
                .build();
    }

}
