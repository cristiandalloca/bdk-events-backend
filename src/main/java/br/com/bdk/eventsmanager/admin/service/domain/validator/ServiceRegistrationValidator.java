package br.com.bdk.eventsmanager.admin.service.domain.validator;

import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.admin.service.domain.model.exception.DuplicateNameServiceException;
import br.com.bdk.eventsmanager.admin.service.domain.repository.ServiceRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceRegistrationValidator implements RegistrationValidator<Service> {

    private final ServiceRepository serviceRepository;

    @Override
    public void validate(@NonNull Service service) throws BusinessException {
        var existingServiceByNameAndCompany = serviceRepository.findByNameIgnoreCaseAndCompanyExternalId(service.getName(), service.getCompany().getExternalId());
        if (existingServiceByNameAndCompany.isPresent() && this.isNotSame(service, existingServiceByNameAndCompany.get())) {
            throw new DuplicateNameServiceException(service.getName());
        }
    }
}
