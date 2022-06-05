package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class AccountNotActiveException extends RuntimeException{
    public AccountNotActiveException() {
    }

    public AccountNotActiveException(String message) {
        super(message);
    }
}
