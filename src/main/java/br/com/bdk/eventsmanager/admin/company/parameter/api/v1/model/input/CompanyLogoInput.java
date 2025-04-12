package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input;

import br.com.bdk.eventsmanager.core.validator.FileContentType;
import br.com.bdk.eventsmanager.core.validator.FileSize;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CompanyLogoInput {

    @NotNull
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @FileSize(max = "5MB")
    private MultipartFile logo;

}
