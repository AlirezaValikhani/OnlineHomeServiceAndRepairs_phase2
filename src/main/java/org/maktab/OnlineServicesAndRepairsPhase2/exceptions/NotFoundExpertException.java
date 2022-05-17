package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class NotFoundExpertException extends RuntimeException{
    public NotFoundExpertException() {
    }

    public NotFoundExpertException(String message) {
        super(message);
    }
}
