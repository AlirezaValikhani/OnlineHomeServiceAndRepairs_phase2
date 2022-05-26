package org.maktab.OnlineServicesAndRepairsPhase2.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotEnoughBalanceException.class)
    public ResponseEntity<Object> notEnoughBalanceException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your balance isn't enough");
    }

    @ExceptionHandler(value = DuplicateNameException.class)
    public ResponseEntity<Object> duplicateNameException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This name already exists!!!");
    }

    @ExceptionHandler(value = DuplicateNationalCodeException.class)
    public ResponseEntity<Object> duplicateNationalCodeException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This national code already exists!!!");
    }

    @ExceptionHandler(value = NotFoundSpecialtyException.class)
    public ResponseEntity<Object> notFoundSpecialtyException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Specialty ID doesn't exists!!!");
    }

    @ExceptionHandler(value = NotFoundExpertException.class)
    public ResponseEntity<Object> notFoundExpertException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no expert with this ID!!!");
    }

    @ExceptionHandler(value = NotFoundCustomerException.class)
    public ResponseEntity<Object> notFoundCustomerException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no customer with this ID!!!");
    }

    @ExceptionHandler(value = NotFoundOrderException.class)
    public ResponseEntity<Object> notFoundOrderException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no order with this ID!!!");
    }

    @ExceptionHandler(value = NotFoundOfferException.class)
    public ResponseEntity<Object> notFoundOfferException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no offer with this ID!!!");
    }

    @ExceptionHandler(value = NotFoundCategoryException.class)
    public ResponseEntity<Object> notFoundCategoryException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no category with this ID!!!");
    }


    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<Object> invalidPasswordException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong password or user name!!!");
    }


    @ExceptionHandler(value = NotFoundWalletException.class)
    public ResponseEntity<Object> notFoundWalletException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This wallet ID already exists!!!");
    }


    @ExceptionHandler(value = AcceptedBeforeException.class)
    public ResponseEntity<Object> acceptedBeforeException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This expert had already been accepted!!!");
    }


    @ExceptionHandler(value = NotFoundWaitingApprovalExpert.class)
    public ResponseEntity<Object> notFoundWaitingApprovalExpert(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no expert waiting for approval!!!");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            ,HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
