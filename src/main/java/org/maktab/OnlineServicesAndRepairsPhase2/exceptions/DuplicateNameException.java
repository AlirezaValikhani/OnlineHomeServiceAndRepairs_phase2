package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class DuplicateNameException extends RuntimeException{
    public DuplicateNameException() {
    }

    public DuplicateNameException(String message) {
        super(message);
    }
}
