package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class WrongFileFormat extends RuntimeException{
    public WrongFileFormat() {
        super();
    }

    public WrongFileFormat(String message) {
        super(message);
    }
}
