package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException() {
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
