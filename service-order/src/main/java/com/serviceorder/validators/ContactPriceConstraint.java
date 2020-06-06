package com.serviceorder.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ContactPriceValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactPriceConstraint {

    String message() default "Invalid price number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
