package com.serviceorder.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 19/06/2020 - 10:15 AM
 * @created_by thuynt
 * @since 19/06/2020
 */
public class NameValidator implements ConstraintValidator<NameConstraint, String> {
    @Override
    public void initialize(NameConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String nameField, ConstraintValidatorContext constraintValidatorContext) {
        return nameField != null && nameField.matches("[A-Z][a-zA-Z][^#&<>\"~;$^%{}?]{1,20}$");
    }
}
