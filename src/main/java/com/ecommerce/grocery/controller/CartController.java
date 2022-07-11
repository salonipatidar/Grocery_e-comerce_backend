package com.ecommerce.grocery.controller;

import com.ecommerce.grocery.common.ApiResponse;
import com.ecommerce.grocery.dto.cart.AddToCartDto;
import com.ecommerce.grocery.dto.cart.CartDto;
import com.ecommerce.grocery.dto.cart.CartItemDto;
import com.ecommerce.grocery.exceptions.AuthenticationFailedException;
import com.ecommerce.grocery.exceptions.ProductNotExistsException;
import com.ecommerce.grocery.model.Cart;
import com.ecommerce.grocery.model.Product;
import com.ecommerce.grocery.model.User;
import com.ecommerce.grocery.repository.CartReposiory;
import com.ecommerce.grocery.service.CartService;
import com.ecommerce.grocery.service.ProductService;
import com.ecommerce.grocery.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "https://salonipatidar.github.io/Grocery_e-commerce_frontend")
public class CartController {

    @Autowired
    CartService cartService ;

    @Autowired
    CartReposiory cartReposiory ;

    @Autowired
    TokenService tokenService ;

    @Autowired
    ProductService productService ;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token){
        //     1 :authenticate the Token
        tokenService.authenticate(token);
//            2: find the user
        User user = tokenService.getUser(token);

        // 3:add to cart


        List<Cart> cart = cartService.getCartForUser(user);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");

        for (Cart item : cart){
            if(addToCartDto.getProductId() == item.getProduct().getId())
            {
                return new ResponseEntity<>(new ApiResponse(false,"Item already in cart"),headers , HttpStatus.OK );

            }
        }


        cartService.addToCart(addToCartDto , user);

        return new ResponseEntity<>(new ApiResponse(true,"added to cart"),headers , HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token)throws AuthenticationFailedException {

        tokenService.authenticate(token);
        User user = tokenService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);

//        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");

        return new ResponseEntity<>(cartDto,HttpStatus.OK );
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId")Integer cartItemId , @RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);

        cartService.deleteCartItem(cartItemId , user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");

        return new ResponseEntity<>(new ApiResponse(true , "Item deleted from cart") ,headers, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse> deleteAll( @RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);

        cartService.deleteAllItemsByUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");

        return new ResponseEntity<>(new ApiResponse(true , "Items deleted from cart"),headers , HttpStatus.OK);
    }
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
                                                      @RequestParam("token") String token) throws AuthenticationFailedException, ProductNotExistsException {
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        Product product = productService.findById(cartDto.getProductId());
        cartService.updateCartItem(cartDto, user,product);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), headers, HttpStatus.OK);
    }
}
