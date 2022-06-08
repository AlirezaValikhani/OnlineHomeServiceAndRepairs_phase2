package org.maktab.OnlineServicesAndRepairsPhase2.validation.secondPassword;

import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.InvalidCardNumberException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.InvalidSecondPasswordException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SecondPasswordConstraintValue implements ConstraintValidator<SecondPassword,String> {
    @Override
    public void initialize(SecondPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.length() != 8)
            throw new InvalidSecondPasswordException("Card Number must have 16 number!!!");

        for (Character ch:s.toCharArray()) {
            if(!Character.isDigit(ch))
                throw new InvalidCardNumberException("Second password must be just number!!!");
        }
        return true;
    }
}
