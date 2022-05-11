package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class InvalidUserName extends RuntimeException{

    public InvalidUserName() {
    }

    public InvalidUserName(String message) {
        super(message);
    }
}
