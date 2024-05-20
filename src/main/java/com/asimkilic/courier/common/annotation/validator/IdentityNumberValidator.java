package com.asimkilic.courier.common.annotation.validator;

import com.asimkilic.courier.common.annotation.ValidIdentityNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdentityNumberValidator implements ConstraintValidator<ValidIdentityNumber, String> {

    @Override
    public boolean isValid(String identityNumber, ConstraintValidatorContext context) {
        if (identityNumber == null || identityNumber.length() != 11) {
            return false;
        }

        if (identityNumber.startsWith("0")) {
            return false;
        }

        if (!identityNumber.matches("\\d+")) {
            return false;
        }


        return isValidIdentityNumber(identityNumber);
    }

    private boolean isValidIdentityNumber(String identityNumber) {
        int[] digits = identityNumber.chars().map(c -> c - '0').toArray();
        int sumOdd = digits[0] + digits[2] + digits[4] + digits[6] + digits[8];
        int sumEven = digits[1] + digits[3] + digits[5] + digits[7];
        int total = sumOdd + sumEven + digits[9];

        boolean rule1 = (sumOdd * 7 - sumEven) % 10 == digits[9];
        boolean rule2 = total % 10 == digits[10];

        return rule1 && rule2;
    }
}
