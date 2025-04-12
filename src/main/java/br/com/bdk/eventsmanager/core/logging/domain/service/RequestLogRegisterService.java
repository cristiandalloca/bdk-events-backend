package br.com.bdk.eventsmanager.core.logging.domain.service;

import br.com.bdk.eventsmanager.core.logging.domain.model.RequestLog;
import br.com.bdk.eventsmanager.core.logging.domain.repository.RequestLogRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class RequestLogRegisterService {

    private final RequestLogRepository requestLogRepository;

    @Async
    @Transactional
    public void save(@Valid RequestLog requestLog) {
        requestLogRepository.save(requestLog);
    }


}
