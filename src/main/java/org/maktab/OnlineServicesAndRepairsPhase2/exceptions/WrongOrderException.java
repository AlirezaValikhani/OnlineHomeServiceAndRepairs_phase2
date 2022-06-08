package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class WrongOrderException extends RuntimeException{
    public WrongOrderException() {
    }

    public WrongOrderException(String message) {
        super(message);
    }
}
