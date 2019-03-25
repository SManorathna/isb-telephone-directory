package com.isb.telephonedirectory.validator.mobilenumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MobileNumberValidator implements ConstraintValidator<IsValidMobileNumber, String> {

    private String mobileNumberRegex = null;

    @Override
    public void initialize(IsValidMobileNumber constraintAnnotation) {
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
