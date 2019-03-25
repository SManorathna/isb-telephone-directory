package com.isb.telephonedirectory.customvalidator.servicetype;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceTypeValidator implements ConstraintValidator<ValidServiceType, String> {

    private List<String> serviceTypeEnumList;

    @Override
    public void initialize(ValidServiceType validServiceType) {
        Class<? extends Enum<?>> enumSelected = validServiceType.enumClass();
        serviceTypeEnumList = getNamesSet(enumSelected);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return serviceTypeEnumList.contains(value);
    }

    private List<String> getNamesSet(Class<? extends Enum<?>> e) {
       return Stream.of(e.getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.toList());
    }
}
