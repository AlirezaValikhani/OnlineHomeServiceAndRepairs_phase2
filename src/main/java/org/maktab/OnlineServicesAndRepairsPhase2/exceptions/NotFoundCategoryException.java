package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

public class NotFoundCategoryException extends RuntimeException{
    public NotFoundCategoryException() {
    }

    public NotFoundCategoryException(String message) {
        super(message);
    }
}
