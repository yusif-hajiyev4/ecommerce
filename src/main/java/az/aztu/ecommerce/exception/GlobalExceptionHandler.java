package az.aztu.ecommerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

import static az.aztu.ecommerce.model.constant.ExceptionConstants.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ExceptionResponse> handle(AuthException ex) {

        log.error("AuthException: {}", ExceptionUtils.getStackTrace(ex));
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ExceptionResponse.of(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(BadRequestException ex) {

        log.error("BadRequest: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handle(NotFoundException ex) {

        log.error("NotFoundException: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception ex) {

        log.error("Exception: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(UNEXPECTED_EXCEPTION_CODE, UNEXPECTED_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(MethodArgumentNotValidException ex) {

        var logMessage = ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .reduce((s1, s2) -> String.format("%s, %s", s1, s2))
                .orElse("");

        log.error("MethodArgumentNotValidException: {}", logMessage);
        return ExceptionResponse.of(BAD_INPUT_CODE, ex.getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(MissingServletRequestParameterException ex) {

        log.error("MissingServletRequestParameterException: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(BAD_INPUT_CODE, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public ExceptionResponse handle(HttpRequestMethodNotSupportedException ex) {

        log.error("HttpRequestMethodNotSupportedException: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(BAD_INPUT_CODE, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(ConstraintViolationException ex) {

        log.error("ConstraintViolationException: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(BAD_INPUT_CODE, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(HttpMessageNotReadableException ex) {

        log.error("HttpMessageNotReadableException: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(BAD_INPUT_CODE, Objects.requireNonNull(ex.getMessage()).split(":")[0]);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(MethodArgumentTypeMismatchException ex) {

        log.error("MethodArgumentTypeMismatchException: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(BAD_INPUT_CODE, Objects.requireNonNull(ex.getMessage()).split(";")[0]);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(MissingRequestHeaderException ex) {

        log.error("MethodArgumentTypeMismatchException: {}", ExceptionUtils.getStackTrace(ex));
        return ExceptionResponse.of(BAD_INPUT_CODE, Objects.requireNonNull(ex.getMessage()).split(";")[0]);
    }
}
