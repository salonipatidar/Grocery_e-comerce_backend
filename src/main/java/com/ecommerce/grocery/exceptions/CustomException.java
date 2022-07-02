package com.ecommerce.grocery.exceptions;

public class CustomException extends IllegalArgumentException {

    public CustomException(String msg){
        super(msg);
    }
}
