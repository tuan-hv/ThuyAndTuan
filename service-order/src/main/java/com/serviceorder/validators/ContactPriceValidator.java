package com.serviceorder.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContactPriceValidator implements ConstraintValidator<ContactPriceConstraint, Integer> {
    @Override
    public void initialize(ContactPriceConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Integer contactField, ConstraintValidatorContext constraintValidatorContext) {
        return contactField != null && (contactField > -1 && contactField <100000000);
    }
}
