package com.isb.telephonedirectory.customvalidator.gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {
        return gender != null && (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F"));
    }
}
