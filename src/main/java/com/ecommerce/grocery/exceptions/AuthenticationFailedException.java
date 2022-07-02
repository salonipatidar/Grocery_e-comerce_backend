package com.ecommerce.grocery.exceptions;

public class AuthenticationFailedException  extends  IllegalArgumentException{

    public AuthenticationFailedException(String msg){
        super(msg);
    }
}
