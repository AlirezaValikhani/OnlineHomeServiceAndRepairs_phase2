package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class BadFileTypeException extends RuntimeException{
    public BadFileTypeException() {
        super();
    }

    public BadFileTypeException(String message) {
        super(message);
    }
}
