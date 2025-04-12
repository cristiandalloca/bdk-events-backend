package br.com.bdk.eventsmanager.admin.service.domain.repository;

import br.com.bdk.eventsmanager.admin.service.domain.model.ServicePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicePhotoRepository extends JpaRepository<ServicePhoto, Long> {

    Optional<ServicePhoto> findByServiceExternalIdAndNumber(@NonNull String serviceExternalId, @NonNull Integer number);

    Optional<ServicePhoto> findByServiceExternalIdAndExternalId(@NonNull String serviceExternalId, @NonNull String externalId);

    List<ServicePhoto> findAllByServiceExternalId(@NonNull String serviceExternalId);

    Optional<ServicePhoto> findByExternalId(@NonNull String externalId);
}
