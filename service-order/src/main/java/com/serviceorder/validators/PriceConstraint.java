package com.serviceorder.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceConstraint {

    String message() default "{invalid.price}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
