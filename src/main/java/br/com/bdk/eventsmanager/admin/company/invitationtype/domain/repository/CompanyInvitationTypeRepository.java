package br.com.bdk.eventsmanager.admin.company.invitationtype.domain.repository;

import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.CompanyInvitationType;
import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.CompanyInvitationType_;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyInvitationTypeRepository extends JpaRepository<CompanyInvitationType, Long> {

    Optional<CompanyInvitationType> findByCompanyExternalIdAndInvitationTypeExternalId(String companyExternalId, String invitationTypeExternalId);

    @EntityGraph(attributePaths = CompanyInvitationType_.INVITATION_TYPE)
    List<CompanyInvitationType> findAllByCompanyExternalId(@NonNull String companyExternalId);

    @EntityGraph(attributePaths = CompanyInvitationType_.INVITATION_TYPE)
    Optional<CompanyInvitationType> findByExternalIdAndCompanyExternalId(String externalId, String companyExternalId);
}
