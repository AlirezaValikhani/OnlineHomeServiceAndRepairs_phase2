package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class NotFoundWalletException extends RuntimeException{
    public NotFoundWalletException() {
    }

    public NotFoundWalletException(String message) {
        super(message);
    }
}
