package org.maktab.OnlineServicesAndRepairsPhase2.validation.password;

import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.InvalidPasswordException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.NotEnoughLengthException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.WrongPasswordLengthException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.length() < 8)
            throw new WrongPasswordLengthException();
        if(!passwordCheck(s))
            return false;
        else return true;
    }

    public Boolean passwordCheck(String password){
        char[] passwordArray = password.toCharArray();
        int lowerCase = 0,upperCase = 0,digit = 0;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isUpperCase(passwordArray[i]))
                ++upperCase;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isLowerCase(passwordArray[i]))
                ++lowerCase;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isDigit(passwordArray[i]))
                ++digit;
        if( ((lowerCase == 0) || (upperCase == 0) || (digit == 0) ))
            throw new InvalidPasswordException();
        else return true;
    }
}
