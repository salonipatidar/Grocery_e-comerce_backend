package com.ecommerce.grocery.controller;

import com.ecommerce.grocery.common.ApiResponse;
import com.ecommerce.grocery.dto.cart.AddToCartDto;
import com.ecommerce.grocery.dto.cart.CartDto;
import com.ecommerce.grocery.model.User;
import com.ecommerce.grocery.service.CartService;
import com.ecommerce.grocery.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService ;

    @Autowired
    TokenService tokenService ;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token){
        //     1 :authenticate the Token
        tokenService.authenticate(token);
//            2: find the user
        User user = tokenService.getUser(token);

        // 3:add to cart

        cartService.addToCart(addToCartDto , user);

        return new ResponseEntity<>(new ApiResponse(true,"added to cart") , HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){

        tokenService.authenticate(token);
        User user = tokenService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId")Integer cartItemId , @RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);

        cartService.deleteCartItem(cartItemId , user);

        return new ResponseEntity<>(new ApiResponse(true , "Item deleted from cart") , HttpStatus.OK);
    }
}
