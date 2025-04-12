package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize maxFileSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        maxFileSize = DataSize.parse(constraintAnnotation.max());
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file == null || file.getSize() <= this.maxFileSize.toBytes();
    }
}
