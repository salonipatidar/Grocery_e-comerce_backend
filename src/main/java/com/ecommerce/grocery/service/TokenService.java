package com.ecommerce.grocery.service;

import com.ecommerce.grocery.exceptions.AuthenticationFailedException;
import com.ecommerce.grocery.model.AuthenticationToken;
import com.ecommerce.grocery.model.User;
import com.ecommerce.grocery.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenService {

    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token){
        AuthenticationToken authenticationToken = tokenRepository.findByToken(token);

        if(Objects.isNull(authenticationToken))
            return null ;

        return  authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthenticationFailedException{
        if(Objects.isNull(token))
            throw new AuthenticationFailedException("Token not present");

        if(Objects.isNull(getUser(token)))
            throw new AuthenticationFailedException("token not valid");
    }
}
