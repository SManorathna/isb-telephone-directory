package com.isb.telephonedirectory.customvalidator.mobilenumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MobileNumberValidator implements ConstraintValidator<ValidMobileNumber, String> {

    private String mobileNumberRegex = null;

    @Override
    public void initialize(ValidMobileNumber constraintAnnotation) {
        mobileNumberRegex = "^\\+?[1-9]\\d{1,14}$";
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && isValidMobileNumber(value);
    }

    private boolean isValidMobileNumber(final String value) {
        return Pattern.compile(mobileNumberRegex).matcher(value).find();
    }
}
