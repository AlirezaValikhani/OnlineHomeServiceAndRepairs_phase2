package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class InvalidCardNumberException extends RuntimeException{
    public InvalidCardNumberException() {
    }

    public InvalidCardNumberException(String message) {
        super(message);
    }
}
