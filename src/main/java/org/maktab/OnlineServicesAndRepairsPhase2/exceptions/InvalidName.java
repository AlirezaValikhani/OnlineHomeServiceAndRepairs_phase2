package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class InvalidName extends RuntimeException{
    public InvalidName() {
    }

    public InvalidName(String message) {
        super(message);
    }
}
