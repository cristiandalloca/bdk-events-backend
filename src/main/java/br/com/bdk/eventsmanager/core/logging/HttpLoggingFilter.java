package br.com.bdk.eventsmanager.core.logging;

import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.admin.user.domain.service.UserRegistrationService;
import br.com.bdk.eventsmanager.auth.domain.model.exception.LoggedUserNotFoundException;
import br.com.bdk.eventsmanager.auth.domain.service.LoggedUserService;
import br.com.bdk.eventsmanager.core.logging.domain.model.RequestLog;
import br.com.bdk.eventsmanager.core.logging.domain.model.RequestLogDetail;
import br.com.bdk.eventsmanager.core.logging.domain.service.RequestLogRegisterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpLoggingFilter extends OncePerRequestFilter {

    private final RequestLogRegisterService requestLogRegisterService;
    private final UserRegistrationService userRegistrationService;
    private final LoggedUserService loggedUserService;
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var requestInitialTime = System.currentTimeMillis();
        var requestWrapper = new ContentCachingRequestWrapper(request);
        var responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        requestLogRegisterService.save(this.assemble(requestWrapper, responseWrapper, System.currentTimeMillis() - requestInitialTime));
        responseWrapper.copyBodyToResponse();
    }

    private RequestLog assemble(ContentCachingRequestWrapper requestWrapper,
                                ContentCachingResponseWrapper responseWrapper,
                                Long durationTimeInMilliseconds) {
        var requestLog = new RequestLog();
        requestLog.setUrl(requestWrapper.getRequestURL().toString());
        requestLog.setMethod(requestWrapper.getMethod());
        requestLog.setIp(this.getIp(requestWrapper));
        requestLog.setAgent(requestWrapper.getHeader(HttpHeaders.USER_AGENT));
        requestLog.setQueryParam(requestWrapper.getQueryString());
        requestLog.setTimeMs(durationTimeInMilliseconds);
        requestLog.setResponseCode(responseWrapper.getStatus());
        requestLog.setUser(this.getLoggedUserOrNull());

        var requestLogDetail = new RequestLogDetail();
        requestLogDetail.setRequestLog(requestLog);
        requestLogDetail.setRequestHeader(this.getRequestHeaders(requestWrapper).toString());
        requestLogDetail.setRequestBody(new String(requestWrapper.getContentAsByteArray()));
        requestLogDetail.setResponseBody(new String(responseWrapper.getContentAsByteArray()));
        requestLogDetail.setResponseHeader(this.getResponseHeaders(responseWrapper).toString());
        requestLog.setDetail(requestLogDetail);

        return requestLog;
    }

    private User getLoggedUserOrNull() {
        try {
            return userRegistrationService.findByIdOrFail(loggedUserService.getUserId());
        } catch (LoggedUserNotFoundException ignored) {
            return null;
        }
    }

    private Map<String, Collection<String>> getRequestHeaders(ContentCachingRequestWrapper requestWrapper) {
        return Collections.list(requestWrapper.getHeaderNames())
                .stream()
                .distinct()
                .collect(Collectors.toMap(Function.identity(), headerName -> Collections.list(requestWrapper.getHeaders(headerName))));
    }

    private Map<String, Collection<String>> getResponseHeaders(ContentCachingResponseWrapper responseWrapper) {
        return responseWrapper.getHeaderNames()
                .stream()
                .distinct()
                .collect(Collectors.toMap(Function.identity(), responseWrapper::getHeaders));
    }

    private String getIp(ContentCachingRequestWrapper requestWrapper) {
        final var ipHeaderCandidates = new String[]{
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        };

        for (String header : ipHeaderCandidates) {
            final var ips = requestWrapper.getHeader(header);
            if (isNotBlank(ips) && !equalsIgnoreCase("unknown", ips)) {
                return ips.split(",")[0];
            }
        }

        return requestWrapper.getRemoteAddr();
    }
}
