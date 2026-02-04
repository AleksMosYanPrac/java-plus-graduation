package ru.practicum.ewm.core.api.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private HttpStatus status;
    private String timestamp;

    public static ApiError from(Exception exception, HttpStatus httpStatus) {
        ApiError apiError = new ApiError();
        apiError.setErrors(Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList());
        apiError.setMessage(exception.getMessage());
        apiError.setReason(Objects.isNull(exception.getCause()) ? null : exception.getCause().getMessage());
        apiError.setStatus(httpStatus);
        apiError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return apiError;
    }
}