package br.com.bdk.eventsmanager.admin.company.parameter.domain.service;

import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.exception.CompanySignatureNotFoundException;
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
public class CompanySignatureRegisterService {

    private final CompanyParameterRegisterService companyParameterRegisterService;
    private final CompanyParameterRepository companyParameterRepository;
    private final StorageFileService storageFileService;

    @Transactional
    public void saveSignature(@NonNull String externalCompanyId, @NonNull MultipartFile signature) {
        var companyParameter = companyParameterRegisterService.findByCompanyExternalIdOrCreate(externalCompanyId);
        companyParameter.setSignatureUri(this.upload(StorageDirectory.COMPANY_SIGNATURE, signature));
        companyParameterRepository.save(companyParameter);
    }

    @Transactional(readOnly = true)
    public URL findSignatureByCompanyExternalId(@NonNull String externalCompanyId) {
        var companyParameter = companyParameterRegisterService.findByCompanyExternalIdOrFail(externalCompanyId);
        final var signatureUri = companyParameter.getSignatureUri();
        if (StringUtils.isBlank(signatureUri)) {
            throw new CompanySignatureNotFoundException(externalCompanyId);
        }

        return storageFileService.getTemporaryUrl(signatureUri);
    }

    @Transactional
    public void removeSignatureByCompanyExternalId(@NonNull String externalCompanyId) {
        var companyParameter = companyParameterRegisterService.findByCompanyExternalId(externalCompanyId);
        companyParameter.ifPresent(parameter -> {
            if (StringUtils.isNotBlank(parameter.getSignatureUri())) {
                storageFileService.delete(parameter.getSignatureUri());
                parameter.setSignatureUri(null);
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
