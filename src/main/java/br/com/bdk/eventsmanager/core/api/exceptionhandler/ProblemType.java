package br.com.bdk.eventsmanager.core.api.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemType {
    INTERNAL_ERROR("Internal error"),
    INVALID_DATA("Invalid data"),
    INVALID_PARAMETER("Invalid parameter"),
    MANDATORY_PARAMETER("Mandatory parameter"),
    RESOURCE_NOT_FOUND("Resource not found"),
    BUSINESS_VIOLATION("Violation of business rules"),
    MAX_UPLOAD_SIZE_EXCEEDED("Maximum upload size exceeded"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    TOKEN_EXPIRED("Token expired"),
    TOKEN_INVALID("Token invalid"),
    RESOURCE_IN_USE("Resource in use");

    private final String title;

}
