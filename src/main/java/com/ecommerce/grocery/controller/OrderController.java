package com.ecommerce.grocery.controller;

import com.ecommerce.grocery.dto.checkout.CheckoutItemDto;
import com.ecommerce.grocery.dto.checkout.StripeResponse;
import com.ecommerce.grocery.service.OrderService;
import com.ecommerce.grocery.service.TokenService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = {"http://localhost:3000" , "https://salonipatidar.github.io" , "https://arpit194.github.io"})
public class OrderController {

    @Autowired
    TokenService tokenService ;

    @Autowired
    OrderService orderService;

//    @ModelAttribute
//    public void setResponseHeader(HttpServletResponse response) {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList );
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return  new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }
}
