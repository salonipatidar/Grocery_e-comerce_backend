package com.ecommerce.grocery.exceptions;

public class ProductNotExistsException extends IllegalArgumentException {
    public ProductNotExistsException(String msg) {
        super(msg);
    }
}
