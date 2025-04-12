package br.com.bdk.eventsmanager.core.api.exceptionhandler;

import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.substringAfterLast;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(@NonNull MaxUploadSizeExceededException exception,
                                                                          @NonNull HttpHeaders headers,
                                                                          @NonNull HttpStatusCode status,
                                                                          @NonNull WebRequest request) {
        var newStatus = HttpStatus.PAYLOAD_TOO_LARGE;
        ProblemType problemType = ProblemType.MAX_UPLOAD_SIZE_EXCEEDED;
        String detail = exception.getMessage();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(detail)
                .type(problemType)
                .status(newStatus.value())
                .build();

        return this.handleExceptionInternal(exception, problem, headers, newStatus, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ProblemType problemType = ProblemType.FORBIDDEN;
        String detail = "Usuário não possui permissão para realizar operação";

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(detail)
                .type(problemType)
                .status(status.value())
                .build();

        return this.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception, WebRequest request) {
        HttpStatusCode status = HttpStatus.UNAUTHORIZED;
        ProblemType problemType = ProblemType.UNAUTHORIZED;
        String userMessage = "Usuário inexistente ou senha inválida";

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(exception.getMessage())
                .userMessage(userMessage)
                .type(problemType)
                .status(status.value())
                .build();

        return this.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusiness(BusinessException exception, WebRequest request) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.BUSINESS_VIOLATION;
        String detail = exception.getMessage();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(detail)
                .type(problemType)
                .status(status.value())
                .build();

        return this.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(ResourceNotFoundException exception, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = exception.getMessage();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(detail)
                .type(problemType)
                .status(status.value())
                .build();
        return this.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Object> handleEntityInUseException(ResourceInUseException exception, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.RESOURCE_IN_USE;
        String detail = exception.getMessage();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage("Não é possível remover um registro que está sendo utilizado")
                .type(problemType)
                .status(status.value())
                .build();

        return this.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.INTERNAL_ERROR;
        String detail = exception.getMessage();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(detail)
                .type(problemType)
                .status(status.value())
                .build();

        return this.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers, @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        return this.handleValidationInternal(ex, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "One or more fields are invalid. Please enter correctly and try again";
        List<ProblemModel.FieldModel> problemFields = ex.getConstraintViolations().stream()
                .map(violation -> {
                    String message = messageSource.getMessage(violation.getMessageTemplate(), null, violation.getMessage(), LocaleContextHolder.getLocale());
                    return ProblemModel.FieldModel.builder()
                            .name(this.getFieldName(violation))
                            .userMessage(message)
                            .build();
                }).toList();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .status(status.value())
                .type(problemType)
                .userMessage(detail)
                .detail(detail)
                .fields(problemFields)
                .build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private String getFieldName(ConstraintViolation<?> violation) {
         String fieldName = violation.getPropertyPath().toString();
        if (violation.getConstraintDescriptor() instanceof ConstraintDescriptorImpl<?> constraintDescriptor
                && constraintDescriptor.getConstraintLocationKind().equals(ConstraintLocation.ConstraintLocationKind.PARAMETER)) {
            fieldName = substringAfterLast(violation.getPropertyPath().toString(), ".");
        }
        return fieldName;
    }

    private ResponseEntity<Object> handleValidationInternal(BindException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "One or more fields are invalid. Please enter correctly and try again";

        BindingResult bindingResult = exception.getBindingResult();
        List<ProblemModel.FieldModel> problemFields = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name;
                    if (objectError instanceof FieldError fieldError) {
                        name = fieldError.getField();
                    } else {
                        name = objectError.getObjectName();
                    }

                    return ProblemModel.FieldModel.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                }).toList();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .status(status.value())
                .type(problemType)
                .userMessage(detail)
                .detail(detail)
                .fields(problemFields)
                .build();

        return this.handleExceptionInternal(exception, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var newStatus = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.MANDATORY_PARAMETER;
        String detail = ex.getMessage();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(detail)
                .type(problemType)
                .status(status.value())
                .build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), newStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var newStatus = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.INVALID_PARAMETER;
        String detail = ex.getMessage();

        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .detail(detail)
                .userMessage(detail)
                .type(problemType)
                .status(status.value())
                .build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), newStatus, request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, @Nullable Object body,
                                                             @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode,
                                                             @NonNull WebRequest request) {
        log.error(ex.getMessage(), ex);
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(JwtException exception, WebRequest request) {
        HttpStatusCode status = HttpStatus.UNAUTHORIZED;
        boolean isExpiredException = exception instanceof ExpiredJwtException;
        ProblemType problemType = isExpiredException ? ProblemType.TOKEN_EXPIRED : ProblemType.TOKEN_INVALID;
        ProblemModel problem = ProblemModel.builder()
                .title(problemType.getTitle())
                .type(problemType)
                .detail(exception.getMessage())
                .userMessage(isExpiredException ? "Token expirado" : "Token inválido")
                .status(status.value())
                .build();

        return this.handleExceptionInternal(exception, problem, new HttpHeaders(), status, request);
    }
}
