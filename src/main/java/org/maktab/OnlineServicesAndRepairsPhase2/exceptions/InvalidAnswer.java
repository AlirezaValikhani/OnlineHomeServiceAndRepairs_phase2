package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class InvalidAnswer extends RuntimeException{
    public InvalidAnswer() {
    }

    public InvalidAnswer(String message) {
        super(message);
    }
}
