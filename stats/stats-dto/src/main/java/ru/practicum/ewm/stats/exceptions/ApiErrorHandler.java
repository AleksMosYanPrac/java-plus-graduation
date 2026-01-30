package ru.practicum.ewm.stats.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;


public interface ApiErrorHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    default ApiError onConstraintViolationException(ConstraintViolationException exception) {
        return ApiError.from(exception, BAD_REQUEST);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    default ApiError onConstraintViolationException(MethodArgumentNotValidException exception) {
        return ApiError.from(exception, BAD_REQUEST);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    default ApiError onNotFoundException(NotFoundException exception) {
        return ApiError.from(exception, NOT_FOUND);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(DataIntegrityViolation.class)
    default ApiError onDataIntegrityViolation(DataIntegrityViolation exception) {
        return ApiError.from(exception, CONFLICT);
    }
}