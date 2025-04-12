package br.com.bdk.eventsmanager.admin.company.invitationtype.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.CompanyInvitationType;
import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.exception.CompanyInvitationTypeNotFoundException;
import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.repository.CompanyInvitationTypeRepository;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.InvitationType;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.service.InvitationTypeRegistrationService;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyInvitationTypeRegisterService {

    private final CompanyRegistrationService companyRegistrationService;
    private final InvitationTypeRegistrationService invitationTypeRegisterService;
    private final CompanyInvitationTypeRepository companyInvitationTypeRepository;

    @Transactional
    public void addInvitationType(@NonNull Company company, @NonNull InvitationType invitationType) {
        var existingCompanyInvitationType = companyInvitationTypeRepository.findByCompanyExternalIdAndInvitationTypeExternalId(company.getExternalId(), invitationType.getExternalId())
                .orElse(new CompanyInvitationType());
        existingCompanyInvitationType.setCompany(companyRegistrationService.findByExternalId(company.getExternalId()));
        existingCompanyInvitationType.setInvitationType(invitationTypeRegisterService.findByExternalId(invitationType.getExternalId()));
        companyInvitationTypeRepository.save(existingCompanyInvitationType);
    }

    @Transactional
    public void removeInvitationType(@NonNull Company company, @NonNull InvitationType invitationType) {
        try {
            var existingCompanyInvitationType = companyInvitationTypeRepository.findByCompanyExternalIdAndInvitationTypeExternalId(company.getExternalId(), invitationType.getExternalId())
                    .orElseThrow(() -> new CompanyInvitationTypeNotFoundException(company.getExternalId(), invitationType.getExternalId()));
            companyInvitationTypeRepository.delete(existingCompanyInvitationType);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException("O tipo de convite parametrizado para empresa está sendo utilizado");
        }
    }

    @Transactional(readOnly = true)
    public List<CompanyInvitationType> findAllInvitationTypesByCompanyExternalId(@NonNull String companyExternalId) {
        return companyInvitationTypeRepository.findAllByCompanyExternalId(companyExternalId);
    }

    @Transactional(readOnly = true)
    public CompanyInvitationType findByExternalIdAndCompanyExternalId(@NonNull String externalId, @NonNull String companyExternalId) {
        return companyInvitationTypeRepository.findByExternalIdAndCompanyExternalId(externalId, companyExternalId)
                .orElseThrow(() -> new CompanyInvitationTypeNotFoundException(externalId));

    }
}
