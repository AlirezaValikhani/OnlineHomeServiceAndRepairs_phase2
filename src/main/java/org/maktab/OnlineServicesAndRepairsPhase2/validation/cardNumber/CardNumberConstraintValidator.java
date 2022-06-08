package org.maktab.OnlineServicesAndRepairsPhase2.validation.cardNumber;

import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.InvalidCardNumberException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardNumberConstraintValidator implements ConstraintValidator<CardNumber,String> {
    @Override
    public void initialize(CardNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.length() != 16)
            throw new InvalidCardNumberException("Card Number must have 16 number!!!");

        for (Character ch:s.toCharArray()) {
            if(!Character.isDigit(ch))
                throw new InvalidCardNumberException();
        }
        return true;
    }
}
