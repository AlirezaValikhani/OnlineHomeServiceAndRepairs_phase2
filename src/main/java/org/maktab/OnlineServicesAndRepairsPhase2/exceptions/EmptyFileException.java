package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class EmptyFileException extends RuntimeException{
    public EmptyFileException() {
        super();
    }

    public EmptyFileException(String message) {
        super(message);
    }
}
