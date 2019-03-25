package com.isb.telephonedirectory.customvalidator.mobilenumber;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMobileNumber {
    String message() default "Subscriber's mobile number is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
