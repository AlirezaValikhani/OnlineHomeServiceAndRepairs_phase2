package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class InvalidImageFormat extends RuntimeException{
    public InvalidImageFormat() {
    }

    public InvalidImageFormat(String message) {
        super(message);
    }
}
