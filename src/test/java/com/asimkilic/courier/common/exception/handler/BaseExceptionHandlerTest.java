package com.asimkilic.courier.common.exception.handler;

import com.asimkilic.courier.common.exception.ErrorDetailDto;
import com.asimkilic.courier.common.exception.ErrorDetailDtoHolder;
import com.asimkilic.courier.common.exception.MessageType;
import com.asimkilic.courier.common.exception.handler.controller.TestController;
import java.util.Collections;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseExceptionHandlerTest {

    @InjectMocks
    private BaseExceptionHandler baseExceptionHandler;

    @Mock
    private MessageSource messageSource;


    @Test
    @SneakyThrows
    void shouldHandleException() {
        //given
        var exception = new Exception();

        when(messageSource.getMessage("internal.server.error", null, LocaleContextHolder.getLocale())).thenReturn("internal.server.error");

        //when
        ResponseEntity<ErrorDetailDtoHolder> response = baseExceptionHandler.handleException(exception);

        //then
        assertThat(response.getBody().getMessage()).isEqualTo("internal.server.error");

        var detail = response.getBody();
        assertThat(detail.getMessage()).isEqualTo("internal.server.error");
        assertNotNull(detail.getMessage());
    }


    @Test
    @SneakyThrows
    void shouldHandleMethodArgumentNotValidExceptionWithValidationType() {
        //given
        var methodParameter = new MethodParameter(TestController.class.getMethod("getObject", Long.class), 0);
        var bindingResult = mock(BindingResult.class);
        var methodArgumentNotValidException = new MethodArgumentNotValidException(methodParameter, bindingResult);
        List<ObjectError> objectErrors = Collections.singletonList(new FieldError("TestController", "courierId", "default.message"));

        when(bindingResult.getAllErrors()).thenReturn(objectErrors);
        when(bindingResult.getTarget()).thenReturn(new TestController());
        when(messageSource.getMessage("error.invalid.request", null, LocaleContextHolder.getLocale())).thenReturn("error.invalid.request");

        //when
        var response = baseExceptionHandler.handleMethodArgumentNotValidException(methodArgumentNotValidException);

        //then
        assertThat(response.getBody().getMessage()).isEqualTo("error.invalid.request");

        ErrorDetailDto detail = response.getBody().getDetails().getFirst();
        assertThat(detail.getType()).isEqualTo(MessageType.VALIDATION.name());
        assertThat(detail.getCode()).isEqualTo("courierId");
        assertThat(detail.getMessage()).isEqualTo("default.message");
        assertNotNull(detail.getMessage());
    }

    @Test
    @SneakyThrows
    void shouldHandleMethodArgumentNotValidExceptionWithBusinessType() {
        //given
        var methodParameter = new MethodParameter(TestController.class.getMethod("getObject", Long.class), 0);
        var bindingResult = mock(BindingResult.class);
        var methodArgumentNotValidException = new MethodArgumentNotValidException(methodParameter, bindingResult);
        List<ObjectError> objectErrors = Collections.singletonList(new ObjectError("courierId", "default.message"));

        when(bindingResult.getAllErrors()).thenReturn(objectErrors);
        when(bindingResult.getTarget()).thenReturn(new TestController());
        when(messageSource.getMessage("error.invalid.request", null, LocaleContextHolder.getLocale())).thenReturn("error.invalid.request");

        //when
        var response = baseExceptionHandler.handleMethodArgumentNotValidException(methodArgumentNotValidException);

        //then
        assertThat(response.getBody().getMessage()).isEqualTo("error.invalid.request");

        var detail = response.getBody().getDetails().getFirst();
        assertThat(detail.getType()).isEqualTo(MessageType.BUSINESS.name());
        assertThat(detail.getCode()).isEqualTo("courierId");
        assertThat(detail.getMessage()).isEqualTo("default.message");
        assertNotNull(detail.getMessage());
    }
}