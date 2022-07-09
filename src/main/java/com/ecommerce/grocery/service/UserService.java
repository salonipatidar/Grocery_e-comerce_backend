package com.ecommerce.grocery.service;

import com.ecommerce.grocery.dto.ResponseDto;
import com.ecommerce.grocery.dto.user.SignInDto;
import com.ecommerce.grocery.dto.user.SignInResponseDto;
import com.ecommerce.grocery.dto.user.SignupDto;
import com.ecommerce.grocery.exceptions.AuthenticationFailedException;
import com.ecommerce.grocery.exceptions.CustomException;
import com.ecommerce.grocery.model.AuthenticationToken;
import com.ecommerce.grocery.model.User;
import com.ecommerce.grocery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        if(Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))){
            throw new CustomException("user already exists");
        }

        String encryptedpassword = signupDto.getPassword();

        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getEmail(), encryptedpassword);

        userRepository.save(user);

        AuthenticationToken authenticationToken = new AuthenticationToken(user);

        tokenService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "user created succesfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto){
        User user = userRepository.findByEmail(signInDto.getEmail());

        if(Objects.isNull(user))
            //throw new AuthenticationFailedException("User/Password is not Valid");
            return new SignInResponseDto("false" , "User/Password is not Valid");

        try{
            if(!user.getPassword().equals(hashPassword(signInDto.getPassword())))
                return new SignInResponseDto("false" , "User/Password is not Valid");
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        AuthenticationToken authenticationToken = tokenService.getToken(user);

        if (Objects.isNull(authenticationToken)) {
            throw new CustomException("token is not present");
        }

        return new SignInResponseDto("success", authenticationToken.getToken());

    }
}

