package com.ecommerce.grocery.controller;

import com.ecommerce.grocery.common.ApiResponse;
import com.ecommerce.grocery.dto.ProductDto;
import com.ecommerce.grocery.model.Product;
import com.ecommerce.grocery.model.User;
import com.ecommerce.grocery.model.WishList;
import com.ecommerce.grocery.service.TokenService;
import com.ecommerce.grocery.service.WishListService;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    WishListService wishListService ;

    @Autowired
    TokenService tokenService;

  //save product as wishlist item
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product , @RequestParam("token") String token){
        //     1 :authenticate the Token
        tokenService.authenticate(token);
//            2: find the user
        User user = tokenService.getUser(token);
//            3: Save the item to wishlist
        WishList wishList = new WishList(user , product);

        wishListService.createWishList(wishList);

        return new ResponseEntity<>(new ApiResponse(true,"Whislist created") , HttpStatus.CREATED);

    }

    // get all wishlist for a user

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token")String token){
        //     1 :authenticate the Token
        tokenService.authenticate(token);
//            2: find the user
        User user = tokenService.getUser(token);
//            3: Show the items in wishlist
        List<ProductDto> wishListForUser =  wishListService.getWhishListForUser(user);

        return new ResponseEntity<>(wishListForUser , HttpStatus.OK);
    }



}
