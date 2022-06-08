package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class InvalidSecondPasswordException extends RuntimeException{
    public InvalidSecondPasswordException() {
    }

    public InvalidSecondPasswordException(String message) {
        super(message);
    }
}
