package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
