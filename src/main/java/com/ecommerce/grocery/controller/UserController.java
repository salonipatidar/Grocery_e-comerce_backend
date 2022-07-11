package com.ecommerce.grocery.controller;

import com.ecommerce.grocery.dto.ResponseDto;
import com.ecommerce.grocery.dto.user.SignInDto;
import com.ecommerce.grocery.dto.user.SignInResponseDto;
import com.ecommerce.grocery.dto.user.SignupDto;
import com.ecommerce.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "https://salonipatidar.github.io")
public class UserController {

    @Autowired
    UserService userService ;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signUp(signupDto);
    }

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "https://salonipatidar.github.io");
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto){
        SignInResponseDto response =  userService.signIn(signInDto);
        if(response.equals("User/Password is not Valid"))
            return new SignInResponseDto("false" , "User/Password is not Valid");

        return response ;
    }
}
