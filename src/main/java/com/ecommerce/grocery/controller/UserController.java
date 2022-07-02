package com.ecommerce.grocery.controller;

import com.ecommerce.grocery.dto.ResponseDto;
import com.ecommerce.grocery.dto.user.SignInDto;
import com.ecommerce.grocery.dto.user.SignInResponseDto;
import com.ecommerce.grocery.dto.user.SignupDto;
import com.ecommerce.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService ;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto){
        return userService.signIn(signInDto);
    }
}
