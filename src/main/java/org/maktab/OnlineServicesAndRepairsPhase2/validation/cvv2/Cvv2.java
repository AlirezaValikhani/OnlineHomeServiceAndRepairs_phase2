package org.maktab.OnlineServicesAndRepairsPhase2.validation.cvv2;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = Cvv2ConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Cvv2 {
    String message() default "must contain jtp";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
