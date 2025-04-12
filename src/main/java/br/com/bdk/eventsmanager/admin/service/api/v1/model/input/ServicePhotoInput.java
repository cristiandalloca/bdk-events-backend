package br.com.bdk.eventsmanager.admin.service.api.v1.model.input;

import br.com.bdk.eventsmanager.core.validator.FileContentType;
import br.com.bdk.eventsmanager.core.validator.FileSize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ServicePhotoInput {

    @JsonIgnore
    private String serviceExternalId;

    @NotNull
    @Max(3)
    @Positive
    @Digits(integer = 1, fraction = 0)
    private Integer number;

    @NotNull
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @FileSize(max = "5MB")
    private MultipartFile photo;

}
