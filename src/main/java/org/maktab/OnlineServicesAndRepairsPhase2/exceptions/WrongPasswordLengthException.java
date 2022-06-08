package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class WrongPasswordLengthException extends RuntimeException{
    public WrongPasswordLengthException() {
    }

    public WrongPasswordLengthException(String message) {
        super(message);
    }
}
