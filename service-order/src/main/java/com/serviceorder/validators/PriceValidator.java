package com.serviceorder.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<PriceConstraint, Double> {
    @Override
    public void initialize(PriceConstraint constraintAnnotation) {
        //todo
    }

    @Override
    public boolean isValid(Double contactField, ConstraintValidatorContext constraintValidatorContext) {
        return contactField != null && (contactField > 0 && contactField <100000000);
    }
}
