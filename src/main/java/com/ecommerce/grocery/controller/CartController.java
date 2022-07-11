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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "https://salonipatidar.github.io")
public class CartController {

    @Autowired
    CartService cartService ;

    @Autowired
    CartReposiory cartReposiory ;

    @Autowired
    TokenService tokenService ;

    @Autowired
    ProductService productService ;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "https://salonipatidar.github.io");
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token){
        //     1 :authenticate the Token
        tokenService.authenticate(token);
//            2: find the user
        User user = tokenService.getUser(token);

        // 3:add to cart


        List<Cart> cart = cartService.getCartForUser(user);



        for (Cart item : cart){
            if(addToCartDto.getProductId() == item.getProduct().getId())
            {
                return new ResponseEntity<>(new ApiResponse(false,"Item already in cart"), HttpStatus.OK );

            }
        }


        cartService.addToCart(addToCartDto , user);

        return new ResponseEntity<>(new ApiResponse(true,"added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token)throws AuthenticationFailedException {

        tokenService.authenticate(token);
        User user = tokenService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);


        return new ResponseEntity<>(cartDto,HttpStatus.OK );
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId")Integer cartItemId , @RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);

        cartService.deleteCartItem(cartItemId , user);


        return new ResponseEntity<>(new ApiResponse(true , "Item deleted from cart") , HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ApiResponse> deleteAll( @RequestParam("token") String token){
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);

        cartService.deleteAllItemsByUser(user);


        return new ResponseEntity<>(new ApiResponse(true , "Items deleted from cart"), HttpStatus.OK);
    }
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
                                                      @RequestParam("token") String token) throws AuthenticationFailedException, ProductNotExistsException {
        tokenService.authenticate(token);
        User user = tokenService.getUser(token);
        Product product = productService.findById(cartDto.getProductId());
        cartService.updateCartItem(cartDto, user,product);

        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }
}
