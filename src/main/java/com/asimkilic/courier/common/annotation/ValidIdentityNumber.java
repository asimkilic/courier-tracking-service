package com.asimkilic.courier.common.annotation;

import com.asimkilic.courier.common.annotation.validator.IdentityNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IdentityNumberValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIdentityNumber {

    String message() default "{invalid.identity-number}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
