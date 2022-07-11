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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "https://salonipatidar.github.io/Grocery_e-commerce_frontend")
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

        List<ProductDto> wishListForUser =  wishListService.getWhishListForUser(user);

        Boolean ans = false ;

        for(ProductDto  newproduct: wishListForUser ){
            if(newproduct.getId() == product.getId()){
                ans = true ;
                break;
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
//            3: Save the item to wishlist
        if(!ans) {
            WishList wishList = new WishList(user, product);

            wishListService.createWishList(wishList);

            return new ResponseEntity<>(new ApiResponse(true,"Whislist created") ,headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ApiResponse(false,"Item already exist in wishlist"),headers , HttpStatus.BAD_REQUEST);

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
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        return new ResponseEntity<>(wishListForUser,headers , HttpStatus.OK);
    }


    @GetMapping("/{token}/{productId}")
    public ResponseEntity<Boolean> isProductInWishList(@PathVariable("token")String token ,@PathVariable("productId") Integer productId){

        tokenService.authenticate(token);
//            2: find the user
        User user = tokenService.getUser(token);
//            3: Show the items in wishlist
        List<ProductDto> wishListForUser =  wishListService.getWhishListForUser(user);

            Boolean ans = false ;

            for(ProductDto  product: wishListForUser ){
                if(product.getId() == productId){
                    ans = true ;
                    break;
                }
            }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        return new ResponseEntity<Boolean>(ans,headers , HttpStatus.OK);
    }

    @DeleteMapping("/{token}/{productId}")
    public ResponseEntity<ApiResponse> deleteFromWishList(@PathVariable("token")String token ,@PathVariable("productId") Integer productId){
        tokenService.authenticate(token);
//            2: find the user
        User user = tokenService.getUser(token);
//            3: Show the items in wishlist
        List<ProductDto> wishListForUser =  wishListService.getWhishListForUser(user);

        Boolean ans = false ;

        for(ProductDto  product: wishListForUser ){
            if(product.getId() == productId){
                ans = true ;
                break;
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");

        if(ans){
            wishListService.deleteItemFromWishList(productId);
            return  new ResponseEntity<>(new ApiResponse(true ,"product deleted from wishlist"),headers, HttpStatus.OK);
        }
        return  new ResponseEntity<>(new ApiResponse(false ,"product does not exist in wishlist"),headers, HttpStatus.OK);

    }

}
