package com.ecommerce.grocery.repository;

import com.ecommerce.grocery.model.Cart;
import com.ecommerce.grocery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartReposiory extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    void deleteAllByUser(User user);
}
