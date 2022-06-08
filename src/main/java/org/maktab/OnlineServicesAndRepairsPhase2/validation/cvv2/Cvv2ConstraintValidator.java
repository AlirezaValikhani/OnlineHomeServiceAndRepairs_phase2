package org.maktab.OnlineServicesAndRepairsPhase2.validation.cvv2;

import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.InvalidCardNumberException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.InvalidCvv2LengthException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Cvv2ConstraintValidator implements ConstraintValidator<Cvv2, String> {
    @Override
    public void initialize(Cvv2 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cvv2, ConstraintValidatorContext constraintValidatorContext) {
        for (Character ch : cvv2.toCharArray()) {
            if (!Character.isDigit(ch))
                throw new InvalidCardNumberException("Cvv2 must be just number!!!");
        }

        if (cvv2.length() != 4)
            throw new InvalidCvv2LengthException();
        else return true;
    }
}
