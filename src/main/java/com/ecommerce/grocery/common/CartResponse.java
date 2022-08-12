package com.ecommerce.grocery.common;

public class CartResponse {
    private  boolean success;
    private String message;
    Integer id ;
    // try again

    public CartResponse(boolean success, String message , Integer id) {
        this.success = success;
        this.message = message;
        this.id = id;
    }



    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getId() {
        return id;
    }
}
