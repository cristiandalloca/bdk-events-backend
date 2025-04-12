package br.com.bdk.eventsmanager.core.logging.domain.repository;

import br.com.bdk.eventsmanager.core.logging.domain.model.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
