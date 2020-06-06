package com.serviceorder.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContactPriceValidator implements ConstraintValidator<ContactPriceConstraint, Double> {
    @Override
    public void initialize(ContactPriceConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Double contactField, ConstraintValidatorContext constraintValidatorContext) {
        return contactField != null && (contactField > 0 && contactField <100000000);
    }
}
