package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class AcceptedBeforeException extends RuntimeException{
    public AcceptedBeforeException() {
    }

    public AcceptedBeforeException(String message) {
        super(message);
    }
}
