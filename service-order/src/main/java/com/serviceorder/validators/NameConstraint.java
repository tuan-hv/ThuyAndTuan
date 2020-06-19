package com.serviceorder.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * ThuyAndTuan
 *
 * @author thuynt
 * @created_at 19/06/2020 - 10:13 AM
 * @created_by thuynt
 * @since 19/06/2020
 */
@Documented
@Constraint(validatedBy = NameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {
    String message() default "{invalid.name}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
