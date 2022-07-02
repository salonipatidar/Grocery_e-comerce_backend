package com.ecommerce.grocery.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = CustomException.class)
    public final ResponseEntity<String> handleCustomException(CustomException exception){
        return  new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthenticationFailedException.class)
    public final ResponseEntity<String> handleAuthenticationFailedException(AuthenticationFailedException exception){
        return  new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ProductNotExistsException.class)
    public final ResponseEntity<String > handleProductNotExistsException(ProductNotExistsException exception){
        return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
