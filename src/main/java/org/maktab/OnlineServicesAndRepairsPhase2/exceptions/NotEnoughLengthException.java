package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class NotEnoughLengthException extends RuntimeException{
    public NotEnoughLengthException() {
    }

    public NotEnoughLengthException(String message) {
        super(message);
    }
}
