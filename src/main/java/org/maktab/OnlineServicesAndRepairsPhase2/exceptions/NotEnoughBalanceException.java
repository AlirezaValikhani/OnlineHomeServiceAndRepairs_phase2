package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class NotEnoughBalanceException extends RuntimeException{
    public NotEnoughBalanceException(String message) {
        super(message);
    }

    public NotEnoughBalanceException() {
    }
}
