package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class NotFoundOrderException extends RuntimeException{
    public NotFoundOrderException() {
    }

    public NotFoundOrderException(String message) {
        super(message);
    }
}
