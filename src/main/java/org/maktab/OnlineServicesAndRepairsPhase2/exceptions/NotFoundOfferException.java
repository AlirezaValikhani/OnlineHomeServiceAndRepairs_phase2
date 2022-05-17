package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class NotFoundOfferException extends RuntimeException{
    public NotFoundOfferException() {
    }

    public NotFoundOfferException(String message) {
        super(message);
    }
}
