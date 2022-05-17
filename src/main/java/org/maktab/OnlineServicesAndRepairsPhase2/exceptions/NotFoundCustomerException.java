package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class NotFoundCustomerException extends RuntimeException{
    public NotFoundCustomerException() {
    }

    public NotFoundCustomerException(String message) {
        super(message);
    }
}
