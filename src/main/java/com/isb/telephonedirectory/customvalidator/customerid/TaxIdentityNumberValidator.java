package com.isb.telephonedirectory.customvalidator.customerid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TaxIdentityNumberValidator implements ConstraintValidator<ValidTaxIdentityNumber, String> {

    private String tinRegex = null;

    @Override
    public void initialize(ValidTaxIdentityNumber constraintAnnotation) {
        tinRegex = "^\\d{7}[M|G|A|P|L|H|B|Z]{1}$";
    }

    @Override
    public boolean isValid(final String value, ConstraintValidatorContext validator) {
        return value != null && isValidTIN(value);
    }

    private boolean isValidTIN(final String value) {
        return Pattern.compile(tinRegex).matcher(value).find();
    }
}
