package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException() {
    }

    public WrongCredentialsException(String message) {
        super(message);
    }
}
