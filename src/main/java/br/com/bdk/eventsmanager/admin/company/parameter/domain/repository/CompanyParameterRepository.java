package br.com.bdk.eventsmanager.admin.company.parameter.domain.repository;

import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.CompanyParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyParameterRepository extends JpaRepository<CompanyParameter, Long> {

    Optional<CompanyParameter> findByCompanyId(@NonNull Long companyId);

}
