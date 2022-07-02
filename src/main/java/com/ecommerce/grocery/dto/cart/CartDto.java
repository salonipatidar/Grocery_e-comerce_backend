package com.ecommerce.grocery.dto.cart;

import java.util.List;

public class CartDto {
    private List<CartItemDto> cartItemDtos;
    private double totalCost;

    public CartDto() {
    }

    public List<CartItemDto> getCartItemDtos() {
        return cartItemDtos;
    }

    public void setCartItemDtos(List<CartItemDto> cartItemDtos) {
        this.cartItemDtos = cartItemDtos;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
