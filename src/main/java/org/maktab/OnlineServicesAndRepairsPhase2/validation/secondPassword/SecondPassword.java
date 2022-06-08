package org.maktab.OnlineServicesAndRepairsPhase2.validation.secondPassword;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SecondPasswordConstraintValue.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface SecondPassword {
    String message() default "must contain jtp";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
