package com.asimkilic.courier.common.exception.handler;

import com.asimkilic.courier.common.exception.ErrorDetailDto;
import com.asimkilic.courier.common.exception.ErrorDetailDtoHolder;
import com.asimkilic.courier.common.exception.MessageType;
import com.asimkilic.courier.common.util.JsonUtil;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class BaseExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDetailDtoHolder> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        String message = messageSource.getMessage("internal.server.error", null, LocaleContextHolder.getLocale());
        ErrorDetailDtoHolder holder = ErrorDetailDtoHolder.builder().message(message).build();
        return new ResponseEntity<>(holder, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDetailDtoHolder> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage(), ex);
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Class<?> targetClass = ex.getBindingResult().getTarget() != null
                ? ex.getBindingResult().getTarget().getClass()
                : null;

        List<ErrorDetailDto> badRequestErrorDtos = errors.stream()
                .map(objectError -> getDtoFromError(targetClass, objectError))
                .sorted(Comparator.comparing(ErrorDetailDto::getMessage))
                .toList();

        ErrorDetailDtoHolder holder = ErrorDetailDtoHolder.builder()
                .message(messageSource.getMessage("error.invalid.request", null, LocaleContextHolder.getLocale()))
                .details(badRequestErrorDtos)
                .build();

        return new ResponseEntity<>(holder, HttpStatus.BAD_REQUEST);
    }

    private ErrorDetailDto getDtoFromError(Class<?> clazz, ObjectError error) {
        String message = error.getDefaultMessage();
        String objectName = error.getObjectName();

        if (error instanceof FieldError fieldError) {
            String field = JsonUtil.getJsonPropertyName(clazz, fieldError.getField());
            return getDtoFromFieldError(field, message);
        }
        return getDtoFromObjectError(objectName, message);
    }

    private ErrorDetailDto getDtoFromFieldError(String field, String message) {
        return ErrorDetailDto.builder()
                .type(MessageType.VALIDATION.name())
                .code(field)
                .message(message)
                .build();

    }

    private ErrorDetailDto getDtoFromObjectError(String objectName, String message) {
        return ErrorDetailDto.builder()
                .type(MessageType.BUSINESS.name())
                .code(objectName)
                .message(message)
                .build();
    }


}
