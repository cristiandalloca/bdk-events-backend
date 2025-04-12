package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes = new ArrayList<>();

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        String[] allowedTypes = constraintAnnotation.allowed();
        if (allowedTypes != null && allowedTypes.length > 0) {
            this.allowedContentTypes = List.of(allowedTypes);
        }
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file == null || allowedContentTypes.contains(file.getContentType());
    }
}
