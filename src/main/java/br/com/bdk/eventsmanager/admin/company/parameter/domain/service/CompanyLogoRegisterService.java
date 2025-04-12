package br.com.bdk.eventsmanager.admin.company.parameter.domain.service;

import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.exception.CompanyLogoNotFoundException;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.repository.CompanyParameterRepository;
import br.com.bdk.eventsmanager.core.storage.StorageDirectory;
import br.com.bdk.eventsmanager.core.storage.StorageFile;
import br.com.bdk.eventsmanager.core.storage.StorageFileService;
import br.com.bdk.eventsmanager.core.util.MultipartFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@Service
@RequiredArgsConstructor
public class CompanyLogoRegisterService {

    private final CompanyParameterRegisterService companyParameterRegisterService;
    private final CompanyParameterRepository companyParameterRepository;
    private final StorageFileService storageFileService;

    @Transactional
    public void saveLogo(@NonNull String externalCompanyId, @NonNull MultipartFile logo) {
        var companyParameter = companyParameterRegisterService.findByCompanyExternalIdOrCreate(externalCompanyId);
        companyParameter.setLogoUri(this.upload(StorageDirectory.COMPANY_LOGO, logo));
        companyParameterRepository.save(companyParameter);
    }

    @Transactional(readOnly = true)
    public URL findLogoByCompanyExternalId(@NonNull String externalCompanyId) {
        var companyParameter = companyParameterRegisterService.findByCompanyExternalIdOrFail(externalCompanyId);
        final var logoUri = companyParameter.getLogoUri();
        if (StringUtils.isBlank(logoUri)) {
            throw new CompanyLogoNotFoundException(externalCompanyId);
        }

        return storageFileService.getTemporaryUrl(logoUri);
    }

    @Transactional
    public void removeLogoByExternalCompanyId(@NonNull String externalCompanyId) {
        var companyParameter = companyParameterRegisterService.findByCompanyExternalId(externalCompanyId);
        companyParameter.ifPresent(parameter -> {
            if (StringUtils.isNotBlank(parameter.getLogoUri())) {
                storageFileService.delete(parameter.getLogoUri());
                parameter.setLogoUri(null);
                companyParameterRepository.save(companyParameter.get());
            }
        });
    }

    private String upload(StorageDirectory directory, MultipartFile file) {
        return storageFileService.upload(StorageFile.builder()
                .directory(directory)
                .fileNameWithExtension(file.getOriginalFilename())
                .file(MultipartFileUtil.toByte(file))
                .build());
    }
}
