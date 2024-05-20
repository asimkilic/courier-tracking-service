package com.asimkilic.courier.common.annotation.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class IdentityNumberValidatorTest {

    private IdentityNumberValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new IdentityNumberValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void shouldInvalid_whenIdentityNumberIsNull() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void shouldInvalid_whenIdentityNumberIsNot11Characters() {
        assertFalse(validator.isValid("1234567890", context));
        assertFalse(validator.isValid("123456789012", context));
    }

    @Test
    void shouldInvalid_whenIdentityNumberHasNonDigitCharacters() {
        assertFalse(validator.isValid("1234567890a", context));
        assertFalse(validator.isValid("12345a78901", context));
    }

    @Test
    void shouldValid_whenIdentityNumberIsValid() {
        // fake valid identity number 95786917406
        String validIdentityNumber = "95786917406";
        assertTrue(validator.isValid(validIdentityNumber, context));
    }

    @Test
    void shouldInvalid_whenIdentityNumberIsInvalid() {
        String invalidIdentityNumber1 = "12345678900";
        String invalidIdentityNumber2 = "00000000000";

        assertFalse(validator.isValid(invalidIdentityNumber1, context));
        assertFalse(validator.isValid(invalidIdentityNumber2, context));
    }
}