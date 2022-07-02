package com.ecommerce.grocery.repository;

import com.ecommerce.grocery.model.AuthenticationToken;
import com.ecommerce.grocery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken , Integer> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}
