package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class DuplicateNationalCodeException extends RuntimeException{
    public DuplicateNationalCodeException() {
    }

    public DuplicateNationalCodeException(String message) {
        super(message);
    }
}
